# WatchApp

## Tecnologias

- Android Support Library
  - AppCompat-v7
  - Recyclerview-v7
  - Cardview-v7
  - Design
- FAB 1.10.1
- Sugar ORM 1.4
- ButterKnife 8.4.0
- Retrofit 2.0.2
- Gson 2.0.2
- Picasso 2.5.2
- EventBus 3.0.0


## Estrutura

```
watchapp/
  app/
    src/main/
      java/app/watchapp
        activity/
          MainActivity.java
        adapter/
          SearchAdapter.java
          WatchAdapter.java
        fragment/
          SearchFragment.java
          WatchFragment.java
        pojo/
          Movie.java
          MovieList.java
        rest/
          service/
            OmbdService.java
          OmdbClient.java
        utils/
          Utils.java
        App.java
      res
      AndroidManifest.xml
  build/
  .gitignore
  build.gradle
  dependecies.gradle
  gradle.properties
  README.md
```
