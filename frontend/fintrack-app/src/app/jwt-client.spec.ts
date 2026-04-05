import { TestBed } from '@angular/core/testing';

import { JwtClient } from './jwt-client';

describe('JwtClient', () => {
  let service: JwtClient;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
