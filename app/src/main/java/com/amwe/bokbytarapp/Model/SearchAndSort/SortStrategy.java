package com.amwe.bokbytarapp.Model.SearchAndSort;

/**
 * Common interface for all sort strategies
 *
 * Used by:
 * ListingAdapter, SortAlphabetically, SortByDate, SortByPrice.
 *
 * @author Magnus Andersson
 */
public interface SortStrategy {
    void sort();
    void sortReversed();
}
