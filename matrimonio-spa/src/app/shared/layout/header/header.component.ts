import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public username: any = localStorage.getItem('user');

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.username = localStorage.getItem('user');
  }

  public userSignOut(): void {
    localStorage.setItem('user', null);
    this.router.navigate(['/home']);
  }

}