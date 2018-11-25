import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public username: any;
  public items: any = [];
  public loader: boolean;

  constructor(private router: Router, private http: HttpClient) {
    this.username = [];
    this.loader = true;
  }

  ngOnInit() {
    localStorage.setItem('user', '');
    this.getData();
  }

  private getData(): void {
    this.loader = true;
    var url = environment.endPoints.getAllUserDisplayNames.url;
    this.http.get(url).subscribe(data => {
      this.items = data;
      this.loader = false;
    }, error => {
    });
  }

  public onSubmit(): void {
    this.router.navigate(['/profile']);
  }

  public selectedUser(value: any): void {
    this.username = value;
    localStorage.setItem('user', this.username.text);
  }

  public removedUser(value: any): void {
    this.username = [];
    localStorage.setItem('user', '');
  }
}
