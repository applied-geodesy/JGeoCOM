/***********************************************************************
* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
*                                                                      *
* This program is free software; you can redistribute it and/or modify *
* it under the terms of the GNU General Public License as published by *
* the Free Software Foundation; either version 3 of the License, or    *
* at your option any later version.                                    *
*                                                                      *
* This program is distributed in the hope that it will be useful,      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
* GNU General Public License for more details.                         *
*                                                                      *
* You should have received a copy of the GNU General Public License    *
* along with this program; if not, see <http://www.gnu.org/licenses/>  *
* or write to the                                                      *
* Free Software Foundation, Inc.,                                      *
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
*                                                                      *
***********************************************************************/

package org.applied_geodesy.instrument.totalstaion.leica.data;

public class TIME_TYPE {
	public int Hour; // 24 hour per day 0..23
	public int Minute; // minute 0..59
	public int Second; // seconds 0..59
	public int CentiSec = 0;
	
	public TIME_TYPE(int hour, int minute, int second) {
		this(hour,minute,second,0);
	}
	
	public TIME_TYPE(int hour, int minute, int second, int centiSec) {
		Hour = hour;
		Minute = minute;
		Second = second;
		CentiSec = centiSec;
	}
}
