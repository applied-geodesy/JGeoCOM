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

public class CAM_OVC_EXTER_CALIB_TYPE {
	public CAM_3D_COORD_TYPE cameraLocation; // 3D camera location with respect to the intersection of Hz and V axes [m]
	public CAM_ROTATION_TYPE cameraRotation; // Hz, V rotation of camera and image rotation [rad]
	
	public CAM_OVC_EXTER_CALIB_TYPE(CAM_3D_COORD_TYPE cameraLocation, CAM_ROTATION_TYPE cameraRotation) {
		this.cameraLocation = cameraLocation;
		this.cameraRotation = cameraRotation;
	}
}
