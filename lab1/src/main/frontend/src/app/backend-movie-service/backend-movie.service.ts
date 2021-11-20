import {Injectable} from '@angular/core';
import {DatePipe} from "@angular/common";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";
import {Movie} from "../model/movie";
import {Page} from "../model/page";

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
      ? this.http.post<Movie>(`${environment.apiUrl}/movies`, movie)
      : this.http.put<Movie>(`${environment.apiUrl}/movies/${id}`, movie);
  }

  getAll(requestParams?: HttpParams): Observable<Page<Movie>> {
    return this.http.get<Page<Movie>>(`${environment.apiUrl}/movies`, {params: requestParams});
  }

  get(id: number): Observable<Movie> {
    return this.http.get<Movie>(`${environment.apiUrl}/movies/${id}`);
  }

  getByMinGenre(): Observable<Movie> {
    return this.http.get<Movie>(`${environment.apiUrl}/movies/genres/min`);
  }

  getByMaxLength(): Observable<Movie> {
    return this.http.get<Movie>(`${environment.apiUrl}/movies/lengths/max`);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/movies/${id}`);
  }

  deleteAll(requestParams?: HttpParams): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/movies`, {params: requestParams});
  }
}
