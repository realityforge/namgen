# TODO

This document is essentially a list of shorthand notes describing work yet to completed.
Unfortunately it is not complete enough for other people to pick work off the list and
complete as there is too much un-said.

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
