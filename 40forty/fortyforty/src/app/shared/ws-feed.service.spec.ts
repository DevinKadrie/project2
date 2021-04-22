import { TestBed } from '@angular/core/testing';

import { WsFeedService } from './ws-feed.service';

describe('WsFeedService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WsFeedService = TestBed.get(WsFeedService);
    expect(service).toBeTruthy();
  });
});
