import {EyeColor} from "./enum/eye-color";
import {HairColor} from "./enum/hair-color";
import {Country} from "./enum/country";
import {Location} from "./location";

export interface Person {
  name?: string,
  height?: number,
  eyeColor?: EyeColor,
  hairColor?: HairColor,
  nationality?: Country,
  location: Location
}
