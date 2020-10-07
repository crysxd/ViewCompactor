This is a very simple library allowing you to dynamically "compact" your Android views if the view would not fit the parent or the activity. 
This is very useful in combination with setting the Activity's `softInputMode` to resize as this will allow seamless transitions when the keyboard opens.

In the `AndroidManifest.xml` set the soft input mode:
```
<activity ... android:windowSoftInputMode="adjustResize" />
```

In e.g. your Fragment apply the `ViewCompactor`:
```
ViewCompactor(view as ViewGroup, reset = {
            Timber.i("Reset")
            TransitionManager.beginDelayedTransition(requireView() as ViewGroup, InstantAutoTransition(quickTransition = true, explode = true))
            full.applyTo(constraintLayout)
            textViewTitle.setTextAppearanceCompat(R.style.OctoTheme_TextAppearance_Title_Large)
        }, compact = { round ->
            Timber.i("Compact $round")
            compact.applyTo(constraintLayout)
            textViewTitle.setTextAppearanceCompat(R.style.OctoTheme_TextAppearance_Title)
            false
        })
```

Now whenever `view` gets resized the `ViewCompactor` measures the view and checks whether it will fit in the available size. If not, it will call the `compact` function 
until it returns `false`. With every `round` you can compact your view further by e.g. hiding views or reducing text sizes. The `reset` function is called before every compaction cycle. Here you can
also use `TransitionManager` as shown above to animate the changes. The `InstantAutoTransition` is a perfect fit for this use case as it will animate all changes
instantly and also animates changes in text size. The option `quickTransition` allows for a speed up transition (150ms) and the `explode` option will move hidden views out instead of fading them out.

Enjoy!
