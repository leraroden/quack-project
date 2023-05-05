import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnvironmentBadgeComponent } from './environment-badge.component';

describe('EnvironmentBadgeComponent', () => {
  let component: EnvironmentBadgeComponent;
  let fixture: ComponentFixture<EnvironmentBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnvironmentBadgeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnvironmentBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
