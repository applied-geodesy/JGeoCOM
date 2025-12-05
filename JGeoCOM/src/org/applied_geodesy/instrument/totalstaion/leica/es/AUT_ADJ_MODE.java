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

package org.applied_geodesy.instrument.totalstaion.leica.es;

public enum AUT_ADJ_MODE {
	AUT_DEFINE_MODE 	(2, "System independent positioning"),
	AUT_POINT_MODE  	(1, "Point tolerance"),
	AUT_NORM_MODE   	(0, "Angle tolerance");
	
	private final int value;
	private String description;
	
	private AUT_ADJ_MODE(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return this.name() + "(" + this.value + "): " + this.description;
	}
	
	public String getDescription() {
		return this.description;
	}

	public static AUT_ADJ_MODE getEnumByValue(int value) {
		for(AUT_ADJ_MODE element : AUT_ADJ_MODE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}