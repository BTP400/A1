package Provider.MaxMind_GeoIPLookup;

import java.util.*;

/**
 * ScannerEx
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Mar 23, 2008, 3:51:27 PM
 */
public class ScannerEx {

private java.util.Scanner sc;

/**
 * Constructor, used to pass the java.util.Scanner object.
 * Note: We cannot subclass java.util.Scanner, because unfortunately
 * it's declared as "final".
 */
public ScannerEx(String s) {
  this.sc = new Scanner(s);
}

/** Returns true if the next token is the beginning of a quoted string value. */
public boolean hasNextQuotedString() {
  return sc.hasNext("\\\".*");
}

/** Scans a quoted string value from the scanner input. */
public String nextQuotedString() {
  if (!sc.hasNext()) throw new java.util.NoSuchElementException();
  if (!hasNextQuotedString()) throw new java.util.InputMismatchException();
  // This is necessary because findInLine would skip over other tokens.
  String s = sc.findInLine("\\\".*?\\\"");
  if (s == null) throw new java.util.InputMismatchException();
  return s.substring(1, s.length() - 1);
}

} // end of class ScannerEx