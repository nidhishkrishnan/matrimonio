import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Options, LabelType } from 'ng5-slider';
import { RequestOptions, ResponseContentType } from '@angular/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private router: Router, private http: HttpClient) {
    this.username = [];
  }

  public username: any;
  public profiles: any = [];
  public loader: boolean = true;
  public filterSearch: boolean = false;
  public noResultsFound: boolean = false;
  public distanceItems: any = [{ text: 'Upper Bound', id: 'Upper Bound' }, { text: 'Lower Bound', id: 'Lower Bound' }];
  public filters: any;

  public resetValues(): void {
    this.filters = {
      hasPhoto: '',
      inContact: '',
      favourite: '',
      compatibility: {
        min: 1, max: 99, options: {
          floor: 1,
          ceil: 99,
          translate: (value: number, label: LabelType): string => {
            switch (label) {
              case LabelType.Low:
                return value + '%';
              case LabelType.High:
                return value + '%';
              default:
                return value + '%';
            }
          }
        }
      },
      age: {
        min: 18, max: 94, options: {
          floor: 18,
          ceil: 94
        }
      },
      height: {
        min: 135, max: 209, options: {
          floor: 135,
          ceil: 209,
          translate: (value: number, label: LabelType): string => {
            switch (label) {
              case LabelType.Low:
                return value + 'cm';
              case LabelType.High:
                return value + 'cm';
              default:
                return value + 'cm';
            }
          }
        }
      },
      distance: []
    };

    this.executeProfileFiltering({});
  }

  public filterProfiles(): void {
    this.noResultsFound = false;
    let allFilters = this.filters;
    var filteringValues: any = {};
    if (allFilters.distance.length != 0) {
      filteringValues.upperBound = allFilters.distance[0].text === 'Upper Bound' ? true : false;
    }
    if (allFilters.inContact !== '') {
      filteringValues.inContact = allFilters.inContact;
    }
    if (allFilters.hasPhoto !== '') {
      filteringValues.hasPhoto = allFilters.hasPhoto;
    }
    if (allFilters.favourite !== '') {
      filteringValues.favourite = allFilters.favourite;
    }
    if (allFilters.compatibility.min != allFilters.compatibility.options.floor || allFilters.compatibility.max != allFilters.compatibility.options.ceil) {
      filteringValues.compatibilityScore = {};
      filteringValues.compatibilityScore.start = allFilters.compatibility.min;
      filteringValues.compatibilityScore.end = allFilters.compatibility.max;
    }
    if (allFilters.height.min != allFilters.height.options.floor || allFilters.height.max != allFilters.height.options.ceil) {
      filteringValues.height = {};
      filteringValues.height.start = allFilters.height.min;
      filteringValues.height.end = allFilters.height.max;
    }
    if (allFilters.age.min != allFilters.age.options.floor || allFilters.age.max != allFilters.age.options.ceil) {
      filteringValues.age = {};
      filteringValues.age.start = allFilters.age.min;
      filteringValues.age.end = allFilters.age.max;
    }

    this.executeProfileFiltering(filteringValues);
  }

  private executeProfileFiltering(filteringValues): void {
    this.loader = true;
    let url = environment.endPoints.filterProfiles.url;
    let loginUserName = localStorage.getItem('user');
    let params = new HttpParams().set("loginUserName", loginUserName);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }), params: params
    };
    this.http.post(url, filteringValues, httpOptions).subscribe(data => {
      this.profiles = data;
      this.noResultsFound = this.profiles.length === 0 ? true: false;
      this.loader = false;
    }, error => {
      this.loader = false;
    });
  }

  public toggleProfileFilter(): void {
    this.filterSearch = !this.filterSearch;
  }

  private getProfileDetails(): void {
    let loginUserName = localStorage.getItem('user');
    if(loginUserName === '' || loginUserName === null) {
      this.router.navigate(['/home']);
    } else {
      this.loader = true;
      this.resetValues();
      let url = environment.endPoints.getAllProfiles.url;
      let params = new HttpParams().set("loginUserName", loginUserName);
      this.http.get(url, { params: params }).subscribe(data => {
        this.profiles = data;
        this.loader = false;
      }, error => {
        this.loader = false;
      });
    }
  }

  ngOnInit() {
    this.getProfileDetails();
  }
}
