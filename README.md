# Albums

<img width="242" alt="Screenshot 2023-06-15 at 11 51 56" src="https://github.com/zinzem/albums/assets/5519152/4a43e3f5-793f-4d8b-adb7-2a780dee7a72">

The [jsonplaceholder](https://jsonplaceholder.typicode.com) api is very convenient to fetch mock data.
This app will fetch albums from that api, and display the three first photos. 
The app uses a cache to avoid fetching from network. The refresh button will empty the cache and fetch
again, then cache the new data.
The app will display an alert dialog if there was any issue fetching the data.

