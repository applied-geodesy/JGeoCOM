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

public class IMG_TCC_CONFIG {
	public static final short IMG_MAX_FILE_PREFIX_LEN = 20;
	public long ulImageNumber;
	public long ulQuality;        // Jpeg compression quality factor (0 – 100)
	public long ulSubFunctNumber; // Binary combination of the following settings:
								  // 1: Test image
								  // 2: Automatic exposure time selection
								  // 4: two-times sub-sampling
								  // 8: four-times sub-sampling
								  // e.g. 6 for Automatic exposure time AND two-times sub-sampling
	public String szFileNamePrefix;
	
	public IMG_TCC_CONFIG(long ulImageNumber, long ulQuality, long ulSubFunctNumber, String szFileNamePrefix) {
		this.ulImageNumber    = Math.abs(ulImageNumber);
		this.ulQuality        = Math.abs(ulQuality);
		this.ulSubFunctNumber = Math.abs(ulSubFunctNumber);
		this.szFileNamePrefix = szFileNamePrefix.length() >= IMG_MAX_FILE_PREFIX_LEN ? szFileNamePrefix.substring(0, IMG_MAX_FILE_PREFIX_LEN) : szFileNamePrefix;
	}
}

