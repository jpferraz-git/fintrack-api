import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../app/services/user.service';


@Component({
  selector: 'app-settings-identity-card',
  imports: [FormsModule],
  templateUrl: './settings-identity-card.component.html',
  styleUrl: './settings-identity-card.component.css'
})

export class SettingsIdentityCard {
  name = ''
  email = ''
  profileSaved = false;
  user: any;

  constructor(private userService: UserService) {}

  ngOnInit() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.name = this.user.name;
      this.email = this.user.email;
    }
  }

  updateUser(): void {
    const updateData = {
      name: this.name,
      email: this.email
    };
    this.userService.updateUser(updateData).subscribe( () => {
        console.log('User updated successfully', updateData);
        this.profileSaved = true
    }
    );
  }


  saveIdentity(): void {
    this.profileSaved = true;
    setTimeout(() => {
      this.profileSaved = false;
    }, 2200);
  }
}
