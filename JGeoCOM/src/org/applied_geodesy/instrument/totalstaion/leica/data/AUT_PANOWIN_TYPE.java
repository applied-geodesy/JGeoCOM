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

import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_COMPRESSION_TYPE;
import org.applied_geodesy.instrument.totalstaion.leica.es.CAM_RESOLUTION_TYPE;

public class AUT_PANOWIN_TYPE {
	public double dWinLeft;                  // Left boundary of the window in rad
	public double dWinRight;                 // Right boundary of the window in rad
	public double dWinTop;                   // Upper boundary of the window in rad
	public double dWinBottom;                // Lower boundary of the window in rad
	public double dOverlapH;                 // Horizontal overlapping (0: no overlapping, 0.99: only 1% is new)
	public double dOverlapV;                 // Vertical overlapping
	public String szImageName;               // Name of the panorama image (single images are named szImageName0xx
	public CAM_RESOLUTION_TYPE eImageRes;	 // Image resolution
	public CAM_COMPRESSION_TYPE eImageCompr; // Image compression
	
	public AUT_PANOWIN_TYPE(double dWinLeft, double dWinRight, double dWinTop, double dWinBottom, double dOverlapH,
			double dOverlapV, String szImageName, CAM_RESOLUTION_TYPE eImageRes, CAM_COMPRESSION_TYPE eImageCompr) {

		this.dWinLeft = dWinLeft;
		this.dWinRight = dWinRight;
		this.dWinTop = dWinTop;
		this.dWinBottom = dWinBottom;
		this.dOverlapH = dOverlapH;
		this.dOverlapV = dOverlapV;
		this.szImageName = szImageName;
		this.eImageRes = eImageRes;
		this.eImageCompr = eImageCompr;
	}
}
