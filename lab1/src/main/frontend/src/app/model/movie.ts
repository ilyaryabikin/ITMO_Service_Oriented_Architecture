import {Coordinates} from "./coordinates";
import {MovieGenre} from "./enum/movie-genre";
import {Person} from "./person";

export interface Movie {
  id?: number,
  name?: string,
  coordinates: Coordinates,
  creationDate?: string,
  oscarsCount?: number,
  budget?: number,
  length?: number,
  genre?: MovieGenre,
  screenwriter: Person
}
