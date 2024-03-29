package org.realityforge.namegen;

import gir.Gir;
import gir.io.FileUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import javax.annotation.Nonnull;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import static org.testng.Assert.*;

@SuppressWarnings( { "WeakerAccess", "SameParameterValue" } )
public abstract class AbstractTest
  implements IHookable
{
  /// Used when generating random string
  @Nonnull
  private static final String STRING_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
  @Nonnull
  private final Random _random = new Random();

  @Override
  public void run( final IHookCallBack callBack, final ITestResult testResult )
  {
    try
    {
      Gir.go( () -> FileUtil.inTempDir( () -> callBack.runTestMethod( testResult ) ) );
    }
    catch ( final Exception e )
    {
      assertNull( e );
    }
  }

  @Nonnull
  protected final Path createTempFile()
    throws IOException
  {
    return Files.createTempFile( FileUtil.getCurrentDirectory(), "namegen", ".txt" );
  }

  @Nonnull
  protected final String readContent( @Nonnull final Path file )
    throws IOException
  {
    return new String( Files.readAllBytes( file ), StandardCharsets.UTF_8 );
  }

  protected final void writeContent( @Nonnull final Path path, @Nonnull final String content )
    throws IOException
  {
    Files.write( path, content.getBytes( StandardCharsets.UTF_8 ) );
  }

  @Nonnull
  protected final Random getRandom()
  {
    return _random;
  }

  protected final int randomPositiveInt()
  {
    return Math.abs( randomInt() );
  }

  protected final int randomInt()
  {
    return getRandom().nextInt();
  }

  protected final double randomPositiveDouble()
  {
    return Math.abs( randomDouble() );
  }

  protected final double randomDouble()
  {
    return getRandom().nextDouble();
  }

  @Nonnull
  protected final String randomString()
  {
    return randomString( 12 );
  }

  @Nonnull
  protected final String randomString( final int length )
  {
    final StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < length; i++ )
    {
      sb.append( STRING_CHARACTERS.charAt( getRandom().nextInt( STRING_CHARACTERS.length() ) ) );
    }
    return sb.toString();
  }
}
