![](https://jitpack.io/v/crysxd/ViewCompactor.svg)

This is a very simple library allowing you to dynamically "compact" your Android views if the view would not fit the parent or the activity. 
This is very useful in combination with setting the Activity's `softInputMode` to resize as this will allow seamless transitions when the keyboard opens.

![](https://github.com/crysxd/ViewCompactor/raw/master/example.gif)

In the `AndroidManifest.xml` set the soft input mode:
```
<activity ... android:windowSoftInputMode="adjustResize" />
```

In e.g. your Fragment apply the `ViewCompactor`:
```
// Using ConstraintSet in example, but you can also manually hide views
val full: ConstraintSet = ...
val compact: ConstraintSet = ...

ViewCompactor(view as ViewGroup, reset = {
  Timber.i("Reset")
  
  // Optional, set up TransitionManager (use androidX one!)
  TransitionManager.beginDelayedTransition(requireView() as ViewGroup, InstantAutoTransition(quickTransition = true, explode = true))
  
  // Reset view to inital "full" state (including text size!)
  full.applyTo(constraintLayout)
  textViewTitle.setTextAppearanceCompat(R.style.OctoTheme_TextAppearance_Title_Large)
}, compact = { round ->
  Timber.i("Compact $round")
  when (round) {
    0 -> {
      // Compact view
      compact.applyTo(constraintLayout)
      textViewTitle.setTextAppearanceCompat(R.style.OctoTheme_TextAppearance_Title)
      true
    }
    
    1 -> {
      // Compact even more....
      true
    }
    
    2 -> {
      // And even more....
      // but after this we can't compact it more, all views left are essential -> return false
      false
    }
  }
})
```

Now whenever `view` gets resized the `ViewCompactor` measures the view and checks whether it will fit in the available size. If not, it will call the `compact` function until it returns `false` or the view fits the available space. With every `round` you can compact your view further by e.g. hiding views or reducing text sizes. The `reset` function is called before every compaction cycle. Here you can
also use `TransitionManager` as shown above to animate the changes. The `InstantAutoTransition` is a perfect fit for this use case as it will animate all changes
instantly and also animates changes in text size. The option `quickTransition` allows for a speed up transition (150ms) and the `explode` option will move hidden views out instead of fading them out.

To use the library add the maven repository to your root `build.gradle`:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

And add the dependency to your local `build.gradle`:

```
dependencies {
  implementation 'com.github.crysxd:ViewCompactor:1.0'
}
```

Enjoy!
