# TODO

This document is essentially a list of shorthand notes describing work yet to completed.
Unfortunately it is not complete enough for other people to pick work off the list and
complete as there is too much un-said.

* Define a `WordSetCatalog` class that will read the `sets.properties` file and provide a catalog of
  all the different word sets available.

## External Projects

* https://github.com/Valkryst/VNameGenerator - generates names using different strategies. Combinator,
  markov chains, crappy letter class combinator and context free generator.
* https://github.com/Valkryst/VParser_CFG - Use a Context Free Grammar to generate data. Could be the
  basis for our "template" language. Used by `VNameGenerator` above.
* https://github.com/ironarachne/world - generates lots of different parts about a world.
  Pantheon, trade goods, languages etc. Interesting for us is language generator that builds
  up using phoneme classes (vowels,fricatives,stops etc). See `pkg/language/language.go`

## Strategies

There are several strategies to derive names that can be implemented. Below is a few ideas.

### Combinator

The "name" is based of a template that combines multiple classes for words. Each class is populated
with a set of words and frequencies. These classes may be statically defined or derived using some other
strategy.

A typical template for male name may be `[MaleIndividualName] [FamilyName]` and then the `MaleIndividualName`
would contain elements like `[{John, 3},{James, 4},{Fred, 0.5},...]` and `FamilyName` would contain names like
`[{Smith,10}, {Jones,5}, {Smith, 2},...]` which would produce names like `John Smith`, `Fred Jones`,
`James Smith` etc. A more advanced templating system may require each word class to satisfy multiple constraints
such as `[male|individual|culture=japanese] [family|culture=japanese]` or even parameterized tpe templates ala
`[male|individual|culture={C}] [family|culture={C}]`.

This strategy produces reasonably realistic names if the input word sets and templates are realistic. However, the
process of creating and tagging names is reasonably easy.

### Linguistic driven

See a description at https://groups.google.com/forum/m/#!msg/rec.games.roguelike.development/cdgIgD3Plds/dkweliggW5AJ
but essentially it involves:

* starting with a subset of phonemes for a language.
* applying rules to combine phonemes together to form words. The rules would be a subset of global rules
  specific to a language.
* Applying rules to convert phonemes to graphemes. 
