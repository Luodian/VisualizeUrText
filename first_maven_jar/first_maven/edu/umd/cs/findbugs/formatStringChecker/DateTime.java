/**
 * 
 */
package edu.umd.cs.findbugs.formatStringChecker;

class DateTime {
	static final char HOUR_OF_DAY_0 = 'H'; // (00 - 23)
	static final char HOUR_0 = 'I'; // (01 - 12)
	static final char HOUR_OF_DAY = 'k'; // (0 - 23) -- like H
	static final char HOUR = 'l'; // (1 - 12) -- like I
	static final char MINUTE = 'M'; // (00 - 59)
	static final char NANOSECOND = 'N'; // (000000000 - 999999999)
	static final char MILLISECOND = 'L'; // jdk, not in gnu (000 - 999)
	static final char MILLISECOND_SINCE_EPOCH = 'Q'; // (0 - 99...?)
	static final char AM_PM = 'p'; // (am or pm)
	static final char SECONDS_SINCE_EPOCH = 's'; // (0 - 99...?)
	static final char SECOND = 'S'; // (00 - 60 - leap second)
	static final char TIME = 'T'; // (24 hour hh:mm:ss)
	static final char ZONE_NUMERIC = 'z'; // (-1200 - +1200) - ls minus?
	static final char ZONE = 'Z'; // (symbol)

	// Date
	static final char NAME_OF_DAY_ABBREV = 'a'; // 'a'
	static final char NAME_OF_DAY = 'A'; // 'A'
	static final char NAME_OF_MONTH_ABBREV = 'b'; // 'b'
	static final char NAME_OF_MONTH = 'B'; // 'B'
	static final char CENTURY = 'C'; // (00 - 99)
	static final char DAY_OF_MONTH_0 = 'd'; // (01 - 31)
	static final char DAY_OF_MONTH = 'e'; // (1 - 31) -- like d
	// * static final char ISO_WEEK_OF_YEAR_2 = 'g'; // cross %y %V
	// * static final char ISO_WEEK_OF_YEAR_4 = 'G'; // cross %Y %V
	static final char NAME_OF_MONTH_ABBREV_X = 'h'; // -- same b
	static final char DAY_OF_YEAR = 'j'; // (001 - 366)
	static final char MONTH = 'm'; // (01 - 12)
	// * static final char DAY_OF_WEEK_1 = 'u'; // (1 - 7) Monday
	// * static final char WEEK_OF_YEAR_SUNDAY = 'U'; // (0 - 53) Sunday+
	// * static final char WEEK_OF_YEAR_MONDAY_01 = 'V'; // (01 - 53) Monday+
	// * static final char DAY_OF_WEEK_0 = 'w'; // (0 - 6) Sunday
	// * static final char WEEK_OF_YEAR_MONDAY = 'W'; // (00 - 53) Monday
	static final char YEAR_2 = 'y'; // (00 - 99)
	static final char YEAR_4 = 'Y'; // (0000 - 9999)

	// Composites
	static final char TIME_12_HOUR = 'r'; // (hh:mm:ss [AP]M)
	static final char TIME_24_HOUR = 'R'; // (hh:mm same as %H:%M)
	// * static final char LOCALE_TIME = 'X'; // (%H:%M:%S) - parse format?
	static final char DATE_TIME = 'c';
	// (Sat Nov 04 12:02:33 EST 1999)
	static final char DATE = 'D'; // (mm/dd/yy)
	static final char ISO_STANDARD_DATE = 'F'; // (%Y-%m-%d)
	// * static final char LOCALE_DATE = 'x'; // (mm/dd/yy)

	static boolean isValid(char c) {
		switch (c) {
		case HOUR_OF_DAY_0:
		case HOUR_0:
		case HOUR_OF_DAY:
		case HOUR:
		case MINUTE:
		case NANOSECOND:
		case MILLISECOND:
		case MILLISECOND_SINCE_EPOCH:
		case AM_PM:
		case SECONDS_SINCE_EPOCH:
		case SECOND:
		case TIME:
		case ZONE_NUMERIC:
		case ZONE:

			// Date
		case NAME_OF_DAY_ABBREV:
		case NAME_OF_DAY:
		case NAME_OF_MONTH_ABBREV:
		case NAME_OF_MONTH:
		case CENTURY:
		case DAY_OF_MONTH_0:
		case DAY_OF_MONTH:
			// * case ISO_WEEK_OF_YEAR_2:
			// * case ISO_WEEK_OF_YEAR_4:
		case NAME_OF_MONTH_ABBREV_X:
		case DAY_OF_YEAR:
		case MONTH:
			// * case DAY_OF_WEEK_1:
			// * case WEEK_OF_YEAR_SUNDAY:
			// * case WEEK_OF_YEAR_MONDAY_01:
			// * case DAY_OF_WEEK_0:
			// * case WEEK_OF_YEAR_MONDAY:
		case YEAR_2:
		case YEAR_4:

			// Composites
		case TIME_12_HOUR:
		case TIME_24_HOUR:
			// * case LOCALE_TIME:
		case DATE_TIME:
		case DATE:
		case ISO_STANDARD_DATE:
			// * case LOCALE_DATE:
			return true;
		default:
			return false;
		}
	}
}