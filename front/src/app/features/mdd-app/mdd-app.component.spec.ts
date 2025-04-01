import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MddAppComponent } from './mdd-app.component';

describe('MddAppComponent', () => {
  let component: MddAppComponent;
  let fixture: ComponentFixture<MddAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MddAppComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MddAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
