import {TestBed} from '@angular/core/testing';

import {BackendMovieService} from './backend-movie.service';

describe('BackendMovieService', () => {
  let service: BackendMovieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BackendMovieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
