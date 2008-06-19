
package com.simlink.core.xml;
/**
 * 本代码源自: http://www.wutka.com/ beanToDtd 1.0
 */
public class TagStyleType
{
	public static final int LOWERCASE = 0;
	public static final int MIXEDCASE = 1;
	public static final int UPPERCASE = 2;

	public static final int NONE = 0;
	public static final int HYPHEN = 1;
	public static final int UNDERSCORE = 2;

	protected int caseType;
	protected int separator;

	public TagStyleType(int theCaseType, int theSeparator)
	{
		if ((theCaseType < LOWERCASE) || (theCaseType > UPPERCASE))
		{
			throw new IllegalArgumentException(
				"Case type must be LOWERCASE, MIXEDCASE or UPPERCASE");
		}

		if ((theSeparator < NONE) || (theSeparator > UNDERSCORE))
		{
			throw new IllegalArgumentException(
				"Separator must be NONE, HYPHEN or UNDERSCORE");
		}

		caseType = theCaseType;
		separator = theSeparator;
	}

	public int getCaseType()
	{
		return caseType;
	}

	public int getSeparator()
	{
		return separator;
	}
}
