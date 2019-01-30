import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAudioComponent } from './manage-audio.component';

describe('ManageAudioComponent', () => {
  let component: ManageAudioComponent;
  let fixture: ComponentFixture<ManageAudioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageAudioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageAudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
