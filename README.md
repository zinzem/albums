# Albums

The [jsonplaceholder](https://jsonplaceholder.typicode.com) api is very convenient to fetch mock data.
This app will fetch albums from that api, and display the three first photos. 
The app uses a cache to avoid fetching from network. The refresh button will empty the cache and fetch
again, then cache the new data.
The app will display an alert dialog if there was any issue fetching the data.

