import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public username: any;
  public profiles: any = [];
  public loader: boolean = true;

  constructor(private router: Router, private http: HttpClient) {
    this.username = [];
  }

  private getProfileDetails(): void {
    this.loader = true;
    let url = environment.endPoints.getAllProfiles.url;
    let params = new HttpParams().set("loginUserName",'fdsfsdfds');
    this.http.get(url, {params: params} ).subscribe(data => {
      this.profiles = data;
      this.loader = false;
    }, error => {
      this.loader = false;
    });
  }

  ngOnInit() {
    this.getProfileDetails();
  }
}
