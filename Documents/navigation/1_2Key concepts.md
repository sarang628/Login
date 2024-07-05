### Host

A UI element that <b>contains the current navigation destination.</b>
That is, when a user navigates through an app, the app essentially
swaps destinations in and out of the navigation host.

Compose: NavHost
Fragments: NavHostFragment

### Graph

A <u>data structure</u> that <b>defines all the navigation destinations</b> within
the app and how they connect together.

NavGraph

### Controller

The <u>central coordinator</u> for <b>managing navigation between destinations.</b>
The controller offers methods for navigating between destinations,
handling deep links, managing the back stack, and more.

NavController

### Destination

A <u>node</u> in the navigation graph. When the user navigates to this node, the host displays its content.

### NavDestination

Typically created when constructing the navigation graph.

### Route

<b>Uniquely identifies a destination</b> and any data required by it.

You can navigate using routes. Routes take you to destinations.