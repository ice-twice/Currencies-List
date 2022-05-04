# Currencies list

This app represents a list of cryptocurrencies with rates. 

The app is able to download a currencies list from a remote source, show it on the screen, put the list into the cache, and refresh the list via swipe to refresh.

Data from the cache is loaded every time if it’s less than 20 seconds from the last successful request to the remote source.

Stack:
Clean Architecture, Navigation, View Binding, View Model, Room, Hilt, Jsoup, Coroutines, LeakCanary.
 
<br>
<div>
	<img src="https://user-images.githubusercontent.com/16716940/129628887-11b15510-4c9a-4dd7-8c24-1e9bd5143df2.png" height="500">
</div>