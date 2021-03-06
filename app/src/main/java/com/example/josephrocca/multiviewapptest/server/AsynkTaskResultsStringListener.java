package com.example.josephrocca.multiviewapptest.server;

/**
 * Activites that wish to be notified about results
 * in onPostExecute of an AsyncTask must implement
 * this interface.
 *
 * This is the basic Observer pattern.
 */
public interface AsynkTaskResultsStringListener
{
    public void onResultsSucceeded(String result);
}