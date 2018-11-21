// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  endPoints: {
    getAllUserDisplayNames:{
      url: 'http://localhost:8080/matrimonio/v1/user/names'
    },
    getAllProfiles:{
      url: 'http://localhost:8080/matrimonio/v1/profile/list'
    },
    category:{
      url: 'http://localhost:8080/matrimonio/category'
    }
  }
};
