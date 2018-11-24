export const environment = {
  production: true,
  endPoints: {
    getAllUserDisplayNames: {
      url: 'http://localhost:8080/matrimonio/v1/profile/names'
    },
    getAllProfiles: {
      url: 'http://localhost:8080/matrimonio/v1/profile/list'
    },
    filterProfiles: {
      url: 'http://localhost:8080/matrimonio/v1/profile/filter'
    }
  }
};
