import {Injectable} from '@angular/core';
import {DatePipe} from "@angular/common";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";
import {Movie} from "../model/movie";
import {Page} from "../model/page";
import {Person} from "../model/person";

@Injectable({
  providedIn: 'root'
})
export class BackendMovieService {

  datePipe: DatePipe = new DatePipe('en-US');

  constructor(private http: HttpClient) {
  }

  save(movie: Movie, id?: number): Observable<Movie> {
    if (movie.creationDate != null) {
      movie.creationDate = this.datePipe.transform(movie.creationDate, 'YYYY-MM-dd')!;
    }
    return id == null
      ? this.http.post<Movie>(`${environment.mainApiUrl}/movies`, movie)
      : this.http.put<Movie>(`${environment.mainApiUrl}/movies/${id}`, movie);
  }

  getAll(requestParams?: HttpParams): Observable<Page<Movie>> {
    return this.http.get<Page<Movie>>(`${environment.mainApiUrl}/movies`, {params: requestParams});
  }

  get(id: number): Observable<Movie> {
    return this.http.get<Movie>(`${environment.mainApiUrl}/movies/${id}`);
  }

  getByMinGenre(): Observable<Movie> {
    return this.http.get<Movie>(`${environment.mainApiUrl}/movies/genres/min`);
  }

  getByMaxLength(): Observable<Movie> {
    return this.http.get<Movie>(`${environment.mainApiUrl}/movies/lengths/max`);
  }

  getLosersMovies(requestParams?: HttpParams): Observable<Page<Movie>> {
    return this.http.get<Page<Movie>>(`${environment.secondaryApiUrl}/movies/get-losers`, {params: requestParams});
  }

  getLosersDirectors(requestParams?: HttpParams): Observable<Page<Person>> {
    return this.http.get<Page<Person>>(`${environment.secondaryApiUrl}/directors/get-losers`, {params: requestParams});
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.mainApiUrl}/movies/${id}`);
  }

  deleteAll(requestParams?: HttpParams): Observable<any> {
    return this.http.delete<any>(`${environment.mainApiUrl}/movies`, {params: requestParams});
  }
}
