import {Movie} from "./movie";
import {Coordinates} from "./coordinates";
import {MovieGenre} from "./enum/movie-genre";
import {Person} from "./person";

export class MovieData implements Movie {

  budget?: number;
  coordinates: Coordinates;
  creationDate?: string;
  genre?: MovieGenre;
  id?: number;
  length?: number;
  name?: string;
  oscarsCount?: number;
  screenwriter: Person;

  public constructor() {
    this.coordinates = {};
    this.screenwriter = {location: {}};
  }
}
