import {Component} from '@angular/core';
import {HttpParams} from "@angular/common/http";
import {FilterMatchMode, LazyLoadEvent, MessageService, PrimeNGConfig} from "primeng/api";
import {Movie} from "../model/movie";
import {HairColor} from "../model/enum/hair-color";
import {EyeColor} from "../model/enum/eye-color";
import {MovieGenre} from "../model/enum/movie-genre";
import {Country} from "../model/enum/country";
import {MovieData} from "../model/movie-data";
import {RequestParams} from "../model/request-params";
import {BackendMovieService} from "../backend-movie-service/backend-movie.service";

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent {

  hairColors: (string | HairColor)[] = Object.values(HairColor).filter(key => isNaN(Number(key)));
  eyeColors: (string | EyeColor)[] = Object.values(EyeColor).filter(key => isNaN(Number(key)));
  genres: (string | MovieGenre)[] = Object.values(MovieGenre).filter(key => isNaN(Number(key)));
  countries: (string | Country)[] = Object.values(Country).filter(key => isNaN(Number(key)));

  isTableLoading: boolean = false;
  isMovieDialogVisible: boolean = false;
  isDeleteByScreenwriterDialogVisible: boolean = false;

  movie: MovieData = new MovieData();
  movies: Movie[] = [];

  currentPage: number = 0;
  lastPage: number = 0;
  totalCount: number = 0;
  currentPageSize: number = 10;

  private pageParams: HttpParams = new HttpParams();

  constructor(private movieService: BackendMovieService,
              private messageService: MessageService,
              private config: PrimeNGConfig) {
    this.config.filterMatchModeOptions = {
      text: [
        FilterMatchMode.EQUALS
      ],
      numeric: [
        FilterMatchMode.EQUALS
      ],
      date: [
        FilterMatchMode.EQUALS
      ]
    };
  }

  onLoad(event: LazyLoadEvent) {
    this.pageParams = new HttpParams();
    event.multiSortMeta?.forEach(meta => {
      this.pageParams = this.pageParams.append(
        RequestParams.SORT_PARAM,
        `${meta.field},${meta.order == 1
          ? RequestParams.SORT_PARAM_ASC
          : RequestParams.SORT_PARAM_DESC}`
      );
    });

    Object.keys(event.filters!).forEach(key => {
      if ((<any>event.filters![key])[0]['value'] != undefined) {
        this.pageParams = this.pageParams.append(key, (<any>event.filters![key])[0]['value']);
      }
    })

    if (event.first != null && event.rows != null) {
      const pageNumber: number = event.first / event.rows;
      this.pageParams = this.pageParams.append(RequestParams.PAGE_NUMBER_PARAM, pageNumber);
      this.pageParams = this.pageParams.append(RequestParams.PAGE_SIZE_PARAM, event.rows);
    }

    this.fetchTableData(this.pageParams);
  }

  openMovieDialog() {
    this.movie = new MovieData();
    this.isMovieDialogVisible = true;
  }

  dismissMovieDialog() {
    this.isMovieDialogVisible = false;
  }

  saveNewMovie() {
    this.isTableLoading = true;
    this.isMovieDialogVisible = false;
    this.movieService.save(this.movie).subscribe({
      next: movie => {
        this.totalCount += 1;
        if (this.movies.length < this.currentPageSize) {
          this.movies.push(movie);
        }
        this.messageService.add({
          severity: 'success',
          summary: 'Movie successfully created'
        });
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error saving movie',
          detail: err.error.message ?? err.message
        });
      }
    }).add(() => {
      this.isTableLoading = false;
    })
  }

  getByMinGenre() {
    this.isTableLoading = true;
    this.movieService.getByMinGenre().subscribe({
      next: movie => {
        this.movies = [];
        this.movies.push(movie);
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error finding movie',
          detail: err.error.message ?? err.message
        });
      }
    }).add(() => {
      this.isTableLoading = false;
    })
  }

  getByMaxLength() {
    this.isTableLoading = true;
    this.movieService.getByMaxLength().subscribe({
      next: movie => {
        this.movies = [];
        this.movies.push(movie);
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error finding movie',
          detail: err.error.message ?? err.message
        });
      }
    }).add(() => {
      this.isTableLoading = false;
    })
  }

  updateMovie(event: any) {
    this.isTableLoading = true;
    this.movieService.save(event.data, event.data['id']).subscribe({
      next: movie => {
        const index: number = this.movies.findIndex(item => item.id === movie.id);
        this.movies[index] = movie;
        this.messageService.add({
          severity: 'success',
          summary: 'Movie successfully updated'
        });
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Movie failed to update',
          detail: err.error.message ?? err.message
        })
      }
    }).add(() => {
      this.isTableLoading = false;
    });
  }

  openDeleteByScreenwriterDialog() {
    this.movie = new MovieData();
    this.isDeleteByScreenwriterDialogVisible = true;
  }

  dismissDeleteByScreenwriterDialog() {
    this.isDeleteByScreenwriterDialogVisible = false;
  }

  deleteByScreenwriterMovies() {
    this.isTableLoading = true;
    this.pageParams = new HttpParams();
    Object.keys(this.movie['screenwriter']).forEach(key => {
      const root = (<any>this.movie['screenwriter']);
      if (root[key] != null) {
        if (key === 'location') {
          Object.keys(root[key]).forEach(location => {
            if (root[key][location] != null) {
              this.pageParams = this.pageParams.append(`screenwriter.${key}.${location}`, root[key][location]);
            }
          });
        } else {
          this.pageParams = this.pageParams.append(`screenwriter.${key}`, root[key]);
        }
      }
    });

    this.isDeleteByScreenwriterDialogVisible = false;
    this.movieService.deleteAll(this.pageParams).subscribe({
      next: result => {
        this.messageService.add({
          severity: 'success',
          summary: 'Movies successfully deleted'
        })
        this.fetchTableData();
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error deleting movies',
          detail: err.error.message ?? err.message
        })
      }
    }).add(() => {
      this.isTableLoading = false;
    });
  }

  private fetchTableData(httpParams?: HttpParams) {
    this.isTableLoading = true;
    this.movieService.getAll(httpParams).subscribe({
      next: page => {
        this.movies = page.result;
        this.currentPage = page.pageNumber;
        this.lastPage = page.lastPage;
        this.totalCount = page.totalCount;
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'API error',
          detail: err.error.message ?? err.message
        })
      }
    }).add(() => {
      this.isTableLoading = false;
    });
  }
}
