package Provider.MaxMind_GeoIPLookup;

import org.apache.commons.lang.*;

import java.util.*;

/**
 * Utils
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Apr 21, 2008, 12:07:14 PM
 */
public class Utils {

public static final String buildUri(String uri, HashMap<String, String> map) throws IllegalArgumentException {
  Validate.notNull(uri, "uri can not be null");

  StringBuilder sb = new StringBuilder();

  sb.append(uri);

  int i = 0;

  if (map != null && !map.isEmpty()) {
    for (String key : map.keySet()) {
      if (i == 0) {
        sb.append("?");
      }
      else {
        sb.append("&");
      }

      sb.append(key);
      sb.append("=");
      sb.append(map.get(key));

      i++;
    }
  }

  return sb.toString();
}

public static String mapToString(Map map) {
  StringBuilder sb = new StringBuilder();

  sb.append("{number of entries=").append(map.size()).append("}\n");

  int i = 0;

  String separator = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

  for (Object key : map.keySet()) {
    if (i == 0 && !map.isEmpty()) sb.append(separator);
    sb.append("\t[").append(++i).append("] ").
        append(key.toString()).append("=\n\t\t ").
        append(map.get(key).toString()).append(separator);
  }

  return sb.toString();
}

}//end class Utils
