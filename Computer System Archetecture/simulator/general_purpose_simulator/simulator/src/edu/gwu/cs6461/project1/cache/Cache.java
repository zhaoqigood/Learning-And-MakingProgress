package edu.gwu.cs6461.project1.cache;

public interface Cache {
    /**
     * The requirements are as follows:
     * cache structure
     * fully associated caches ( no sets);
     * 16 cache lines and 8 words for each line ( For the address, 9 bits are tag bits, 3 bits are offset bits.
     * The line replacement policy are FIFO
     *
     * Method implementations
     * For the read implementation, if the tag is hit, then return the value directly;
     *                              if the tag is miss, then get a page from the memory, and search the cache again.
     * For the write implementation, if the tag is hit, then write to the cache directly and then write the whole line to the memory.
     *                               if the tag is miss, then write to the memory directly.
     * Attention: memory should be reorganized as combination of  8-words pages, such that these pages will not overlapping in the cache.
     *
     */
    void setValue(short address, short word);
    short getValue(short address);
}
