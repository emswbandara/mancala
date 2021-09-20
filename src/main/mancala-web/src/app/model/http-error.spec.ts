import { HttpErrorInterceptor } from './http-error';

describe('HttpError', () => {
  it('should create an instance', () => {
    expect(new HttpErrorInterceptor()).toBeTruthy();
  });
});
