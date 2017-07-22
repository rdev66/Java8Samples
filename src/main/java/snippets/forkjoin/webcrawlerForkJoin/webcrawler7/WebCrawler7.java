/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snippets.forkjoin.webcrawlerForkJoin.webcrawler7;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import snippets.forkjoin.webcrawlerForkJoin.webcrawler7.net.LinkFinderAction;
import java.util.HashSet;

/**
 *
 * @author Madalin Ilie
 */
public class WebCrawler7 implements LinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ForkJoinPool mainPool;

    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    private void startCrawling() {
        mainPool.invoke(new LinkFinderAction(this.url, this));
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new WebCrawler7("http://javaworld.com", 64).startCrawling();
    }
}