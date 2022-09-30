import java.util.*;

public class Main
{
  public static char[] alphabets =
    { 'a', 'b', 'c', 'd', 'd','e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
  };
  public static void main (String[]args)
  {
    Scanner input = new Scanner (System.in);
    String key = input.nextLine ();
    String plainText = input.nextLine ();
    encrypt (key, plainText);



  }

  static void encrypt (String key, String input)
  {
    Map < Character, HashMap < Integer, Integer > >map = setCypher (key);
    Map < HashMap < Integer, Integer >, Character > rmap = rSetCypher (key);
    ArrayList < Character > r = new ArrayList < Character > (), sortResult =
      new ArrayList < Character > ();


    if (input.length () % 2 == 0)
      {
	sortResult = checkForDuplicates (input);
      }
    else
      {
	sortResult = checkForDuplicates (input + "x");
      }

    for (int i =0; i < sortResult.size ();i+=2)
      {
	if (i % 2 == 0 && i < sortResult.size() - 1)
	  {
	    HashMap < Integer, Integer > temp =
	      new HashMap < Integer, Integer > (), temp2 =
	      new HashMap < Integer, Integer > ();
	    if (getKey (map.get (sortResult.get (i))) !=
		getKey (map.get (sortResult.get (i + 1)))
		&& getValue (map.get (sortResult.get (i))) !=
		getValue (map.get (sortResult.get (i + 1))))
	      {
		    temp.put (getKey (map.get (sortResult.get (i))) ,
			      getValue (map.get (sortResult.get (i+1))));
		    temp2.put (getKey (map.get (sortResult.get (i+1))),
			       getValue (map.get (sortResult.get (i))));
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));
		  }
	      
	    else if (getKey (map.get (sortResult.get (i))) !=
		     getKey (map.get (sortResult.get (i + 1)))
		     && getValue (map.get (sortResult.get (i))) ==
		     getValue (map.get (sortResult.get (i + 1))))
	      {
		if (getKey (map.get (sortResult.get (i))) == 4)
		  {

		    temp.put (0, getValue (map.get (sortResult.get (i))));
		    temp2.put (getKey (map.get (sortResult.get (i+1))) + 1,
			       getValue (map.get (sortResult.get (i+1))));
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));
		  }
		else if (getKey (map.get (sortResult.get (i + 1))) == 4)
		  {
		    temp.put (getKey (map.get (sortResult.get (i))) + 1,
			      getValue (map.get (sortResult.get (i))));
		    temp2.put (0, getValue (map.get (sortResult.get (i+1))));
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));

		  }
		else
		  {
		    temp.put (getKey (map.get (sortResult.get (i))) + 1,
			      getValue (map.get (sortResult.get (i))));
		    temp2.put (getKey (map.get (sortResult.get (i+1))) + 1,
			       getValue (map.get (sortResult.get (i+1))));
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));
		  }
	      }
	    else if (getKey (map.get (sortResult.get (i))) ==
		     getKey (map.get (sortResult.get (i + 1)))
		     && getValue (map.get (sortResult.get (i))) !=
		     getValue (map.get (sortResult.get (i + 1))))
	      {
		if (getValue (map.get (sortResult.get (i))) == 4)
		  {

		    temp.put (getKey (map.get (sortResult.get (i))), 0);
		    temp2.put (getKey (map.get (sortResult.get (i+1))),
			       getValue (map.get (sortResult.get (i+1))) + 1);
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));
		  }
		else if (getValue (map.get (sortResult.get (i + 1))) == 4)
		  {
		    temp.put (getKey (map.get (sortResult.get (i))),
			      getValue (map.get (sortResult.get (i))) + 1);
		    temp2.put (getKey (map.get (sortResult.get (i+1))), 0);
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));

		  }
		else
		  {
		    temp.put (getKey (map.get (sortResult.get (i))),
			      getValue (map.get (sortResult.get (i))) + 1);
		    temp2.put (getKey (map.get (sortResult.get (i+1))),
			       getValue (map.get (sortResult.get (i+1))) + 1);
		    r.add (rmap.get (temp));
		    r.add (rmap.get (temp2));
		  }
	      }
	  }
      }

    System.out.println ("Key text: " + key.toUpperCase());
    System.out.println ("Plain text: " + input.toLowerCase());
    System.out.println ("Cypher text: " + getStringRep(r).toUpperCase());
  }
  
 static String getStringRep(ArrayList<Character> list){
      StringBuilder builder = new StringBuilder(list.size());
      for(Character ch: list){
          builder.append(ch);
      }
      
      return builder.toString();
  }
  

  static int getKey (HashMap < Integer, Integer > value)
  {
       int key = 0;
    
   
    if(value != null && value.keySet() != null) key =  (Integer) value.keySet ().toArray()[0];


    return key;
  }

  static int getValue (HashMap < Integer, Integer > value)
  {
    int v = 0;
  if(value != null && value.values() != null) v = (Integer)  value.values ().toArray()[0];

    return v;
  }


  static ArrayList < Character > checkForDuplicates (String plainText)
  {
    char[] arr = toArrayOfCharacters (plainText);

    ArrayList < Character > list = new ArrayList < Character > ();
    for (int i = 0; i < plainText.length (); i++)
      {
	list.add (plainText.charAt (i));
      }

    while (analyze (list, arr.length) == true)
      {
	ArrayList < Integer > arre = analysisResult (list, arr.length);
	for (int z = 0; z < arre.size (); z++)
	  {
	    list.add (arre.get (z), 'x');
	  }

      }
    return list;
  }

  static boolean analyze (ArrayList < Character > arr, int length)
  {
    boolean value = false;
    for (int i = 0; i < arr.size (); i++)
      {
	if (i % 2 != 0 && arr.get (i) == arr.get (i - 1))
	  {
	    value = true;
	  }
      }
    return value;
  }

  static ArrayList < Integer > analysisResult (ArrayList <
					       Character > arr, int length)
  {
    ArrayList < Integer > arre = new ArrayList <> ();
    for (int i = 0; i < arr.size (); i++)
      {
	if (i % 2 != 0 && arr.get (i) == arr.get (i - 1) && arre.size () != 1)
	  {
	    arre.add (i);
	  }
      }
    return arre;
  }

  static Map < Character, HashMap < Integer, Integer > >setCypher (String key)
  {
    char[] arr = toArrayOfCharacters (key.toLowerCase ());

    Map < Character, HashMap < Integer, Integer > >map =
      new HashMap < Character, HashMap < Integer, Integer > >();
    int index = 0, p = 0;
    int indexAlph = 0;
    char[] check = new char[27];
    for (int x = 0; x < 5; x++)
      {
	for (int y = 0; y < 5; y++)
	  {
	    HashMap < Integer, Integer > temp =
	      new HashMap < Integer, Integer > ();
	    temp.put (x, y);
	    if (index < arr.length)
	      {
		if (!characterExists (check, arr[index]))	//"PLAYFAIR"
		  {
		    map.put (arr[index], temp);
		    check[index] = arr[index];
		    index++;
		  }
		else
		  {
		    map.put (arr[index + 1], temp);
		    check[index] = arr[index + 1];
		    index += 2;
		  }
	      }
	    else
	      {
		if ((index + p) < check.length && indexAlph < 26)
		  {
		    if (!characterExists
			(check, alphabets[indexAlph])
			&& alphabets[indexAlph] != 'i'
			&& alphabets[indexAlph] != 'j')
		      {
			map.put (alphabets[indexAlph], temp);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else if (alphabets[indexAlph] == 'i'
			     && !characterExists (check, 'i')
			     && !characterExists (check, 'j'))
		      {

			map.put (alphabets[indexAlph], temp);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else if (alphabets[indexAlph] == 'j'
			     && !characterExists (check, 'i')
			     && !characterExists (check, 'j'))
		      {

			map.put (alphabets[indexAlph], temp);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else
		      {
			if (alphabets[indexAlph] != 'j'
			    && alphabets[indexAlph] != 'i')
			  {
			    map.put (alphabets[indexAlph + 1], temp);
			    check[index + p] = alphabets[indexAlph + 1];
			    indexAlph += 2;
			  }
			else
			  {
			    map.put (alphabets[indexAlph + 2], temp);
			    check[index + p] = alphabets[indexAlph + 2];
			    indexAlph += 3;
			  }
		      }
		    p++;
		  }
	      }
	  }
      }

    return map;
  }

  static Map < HashMap < Integer, Integer >,
    Character > rSetCypher (String key)
  {
    char[] arr = toArrayOfCharacters (key.toLowerCase ());

    Map < HashMap < Integer, Integer >, Character > map =
      new HashMap < HashMap < Integer, Integer >, Character > ();
    int index = 0, p = 0;
    int indexAlph = 0;
    char[] check = new char[27];
    for (int x = 0; x < 5; x++)
      {
	for (int y = 0; y < 5; y++)
	  {
	    HashMap < Integer, Integer > temp =
	      new HashMap < Integer, Integer > ();
	    temp.put (x, y);
	    if (index < arr.length)
	      {
		if (!characterExists (check, arr[index]))	//"PLAYFAIR"
		  {
		    map.put (temp, arr[index]);
		    check[index] = arr[index];
		    index++;
		  }
		else
		  {
		    map.put (temp, arr[index + 1]);
		    check[index] = arr[index + 1];
		    index += 2;
		  }
	      }
	    else
	      {
		if ((index + p) < check.length && indexAlph < 26)
		  {
		    if (!characterExists
			(check, alphabets[indexAlph])
			&& alphabets[indexAlph] != 'i'
			&& alphabets[indexAlph] != 'j')
		      {
			map.put (temp, alphabets[indexAlph]);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else if (alphabets[indexAlph] == 'i'
			     && !characterExists (check, 'i')
			     && !characterExists (check, 'j'))
		      {

			map.put (temp, alphabets[indexAlph]);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else if (alphabets[indexAlph] == 'j'
			     && !characterExists (check, 'i')
			     && !characterExists (check, 'j'))
		      {

			map.put (temp, alphabets[indexAlph]);
			check[index + p] = alphabets[indexAlph];
			indexAlph++;
		      }
		    else
		      {
			if (alphabets[indexAlph] != 'j'
			    && alphabets[indexAlph] != 'i')
			  {
			    map.put (temp, alphabets[indexAlph + 1]);
			    check[index + p] = alphabets[indexAlph + 1];
			    indexAlph += 2;
			  }
			else
			  {
			    map.put (temp, alphabets[indexAlph + 2]);
			    check[index + p] = alphabets[indexAlph + 2];
			    indexAlph += 3;
			  }
		      }
		    p++;
		  }
	      }
	  }
      }

    return map;
  }



  static boolean characterExists (char[]check, char v)
  {

    return (new String (check).contains (String.valueOf (v)));
  }

  static char[] toArrayOfCharacters (String str)
  {
    char[] arr = new char[str.length ()];
    for (int i = 0; i < str.length (); i++)
      {
	arr[i] = str.charAt (i);
      }
    return arr;
  }
}
