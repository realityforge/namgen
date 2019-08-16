package org.realityforge.namegen;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class WordFrequencySetTest
  extends AbstractTest
{
  @Test
  public void basicOperation()
    throws Exception
  {
    final Path file = createTempFile();
    writeContent( file, "fred\njohn,2\npete" );

    final String key = randomString();
    final String tag1 = randomString();
    final String tag2 = randomString();
    final String tag3 = randomString();

    final WordFrequencySet set = WordFrequencySet.from( key, file, tag1, tag2, tag3 );

    assertEquals( set.getKey(), key );
    assertEquals( set.getSource(), file.toString() );
    assertEquals( set.getTags().size(), 3 );
    assertTrue( set.getTags().contains( tag1 ) );
    assertTrue( set.getTags().contains( tag2 ) );
    assertTrue( set.getTags().contains( tag3 ) );
    assertFalse( set.getTags().contains( randomString() ) );

    final List<WordFrequencySet.Word> words = set.getWords();
    assertEquals( words.size(), 3 );
    final WordFrequencySet.Word word1 = words.get( 0 );
    final WordFrequencySet.Word word2 = words.get( 1 );
    final WordFrequencySet.Word word3 = words.get( 2 );
    assertEquals( word1.getWord(), "fred" );
    assertEquals( word1.getWeight(), 1 );
    assertEquals( word2.getWord(), "john" );
    assertEquals( word2.getWeight(), 2 );
    assertEquals( word3.getWord(), "pete" );
    assertEquals( word1.getWeight(), 1 );

    final String names = generateRandomNames( set, 10, new Random( 47 ) );
    assertEquals( names, "pete,john,pete,fred,fred,pete,fred,john,pete,pete" );
    final String names2 = generateRandomNames( set, 10, new Random( 47 ) );
    assertEquals( names2, names );
  }

  @SuppressWarnings( "SameParameterValue" )
  @Nonnull
  private String generateRandomNames( final WordFrequencySet set, final int count, final Random random )
  {
    final StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < count; i++ )
    {
      if ( 0 != i )
      {
        sb.append( "," );
      }
      sb.append( set.select( random ) );
    }
    return sb.toString();
  }
}
