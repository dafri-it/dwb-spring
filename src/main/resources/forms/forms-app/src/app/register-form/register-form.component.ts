import {Component, OnInit} from '@angular/core';
import {Registration} from '../../registration';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-register-form',
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css'
})
export class RegisterFormComponent implements OnInit {

  registration: Registration = {
    firstName: "David",
    lastName: "Fritsche",
    email: "daff88@mail.com",
    participants: [
      {
        email: "du@mail.com",
        firstName: "Du",
        lastName: "Mail"
      },
      {
        firstName: "Ich",
        lastName: "Teste",
        email: "tester@mail.com"
      }]
  };

  registerForm = new FormGroup({
    firstName: new FormControl(this.registration.firstName),
    lastName: new FormControl(this.registration.lastName),
    email: new FormControl(this.registration.email),
    participants: new FormArray(this.registration.participants.map((p) => new FormGroup({
      firstName: new FormControl(p.firstName),
      lastName: new FormControl(p.lastName),
      email: new FormControl(p.email),
    })))
  });

  get participants() {
    return this.registerForm.get('participants') as FormArray<FormGroup>;
  }

  ngOnInit(): void {
    this.registerForm.patchValue({
      email: 'david@fritsche.com'
    })
  }

  addParticipant() {
    this.participants.push(new FormGroup({
        firstName: new FormControl(''),
        lastName: new FormControl(''),
        email: new FormControl('')
      }
    ))
  }

  removeParticipant(index: number) {
    if (index !== -1 && index <= this.participants.length) {
      this.participants.removeAt(index);
    }
  }

  onSubmit() {
    console.log(this.registerForm.value);
  }
}
