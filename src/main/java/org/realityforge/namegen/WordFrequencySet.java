package org.realityforge.namegen;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nonnull;

public final class WordFrequencySet
{
  @Nonnull
  private final String key;
  @Nonnull
  private final String source;
  @Nonnull
  private final Set<String> tags;
  @Nonnull
  private final List<Word> words;
  private final int totalWeight;

  @Nonnull
  public static WordFrequencySet from( @Nonnull final String key,
                                       @Nonnull final Path path,
                                       @Nonnull final String... tags )
    throws IOException
  {
    try ( final InputStream inputStream = new FileInputStream( path.toFile() ) )
    {
      try ( final Reader reader = new InputStreamReader( inputStream ) )
      {
        return from( key, path.toString(), reader, tags );
      }
    }
  }

  @Nonnull
  public static WordFrequencySet from( @Nonnull final String key,
                                       @Nonnull final String source,
                                       @Nonnull final Reader input,
                                       @Nonnull final String... tags )
    throws IOException
  {
    try ( final BufferedReader bufferedReader = new BufferedReader( input ) )
    {
      final List<String> lines = new ArrayList<>();
      String line;
      while ( null != ( line = bufferedReader.readLine() ) )
      {
        lines.add( line );
      }
      return from( key, source, lines, tags );
    }
  }

  @Nonnull
  private static WordFrequencySet from( @Nonnull final String key,
                                        @Nonnull final String source,
                                        @Nonnull final List<String> lines,
                                        @Nonnull final String[] tags )
  {
    final ArrayList<Word> words = new ArrayList<>();

    for ( final String line : lines )
    {
      final String l = line.trim();
      if ( !l.isEmpty() )
      {
        final int index = l.lastIndexOf( ',' );
        final int weight = -1 == index ? 1 : Integer.parseInt( l.substring( index + 1 ) );
        final String word = -1 == index ? l : l.substring( 0, index );
        words.add( new Word( word, weight ) );
      }
    }

    final HashSet<String> tagSet = new HashSet<>();
    Collections.addAll( tagSet, tags );
    return new WordFrequencySet( key, source, tagSet, words );
  }

  private WordFrequencySet( @Nonnull final String key,
                            @Nonnull final String source,
                            @Nonnull final Set<String> tags,
                            @Nonnull final List<Word> words )
  {
    this.key = Objects.requireNonNull( key );
    this.source = Objects.requireNonNull( source );
    this.tags = new HashSet<>( tags );
    this.words = new ArrayList<>( words );
    this.totalWeight = words.stream().map( Word::getWeight ).reduce( Integer::sum ).orElse( 0 );
  }

  @Nonnull
  public String getKey()
  {
    return key;
  }

  @Nonnull
  public String getSource()
  {
    return source;
  }

  @Nonnull
  public Set<String> getTags()
  {
    return Collections.unmodifiableSet( tags );
  }

  @Nonnull
  public List<Word> getWords()
  {
    return Collections.unmodifiableList( words );
  }

  @Nonnull
  public String select( @Nonnull final Random random )
  {
    // This method is extremely inefficient but until there is a need to speed it up we can live with it
    int value = random.nextInt( totalWeight );
    for ( final Word word : words )
    {
      if ( value <= 0 )
      {
        return word.getWord();
      }
      else
      {
        value -= word.getWeight();
      }
    }
    // It should be impossible to get here
    assert false;
    return "";
  }

  public static final class Word
  {
    @Nonnull
    private final String word;
    private final int weight;

    public Word( @Nonnull final String word, final int weight )
    {
      assert weight > 0;
      this.word = word;
      this.weight = weight;
    }

    @Nonnull
    public String getWord()
    {
      return word;
    }

    public int getWeight()
    {
      return weight;
    }
  }
}
