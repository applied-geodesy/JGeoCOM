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

public enum CAM_JPEG_COMPR_QUALITY_TYPE {
	
	CAM_JPGQ_STANDARD (0, "Standard"),
	CAM_JPGQ_BEST     (1, "Best"),
	CAM_JPGQ_IGNORE   (2, "Ignore, e.g. if raw image is selected, the compression quality doesn't have any effect");

	private final int value;
	private String description;
	
	private CAM_JPEG_COMPR_QUALITY_TYPE(int value, String description) {
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

	public static CAM_JPEG_COMPR_QUALITY_TYPE getEnumByValue(int value) {
		for(CAM_JPEG_COMPR_QUALITY_TYPE element : CAM_JPEG_COMPR_QUALITY_TYPE.values()) {
			if(element.getValue() == value)
				return element;
		}
		return null;
	}  
}
