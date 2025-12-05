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

public class TMC_FULL_MEAS {
	public double rdHzAngle;
	public double rdVAngle;
	public double rdAccuracyAngle;
	public double rdCrossIncl;
	public double rdLengthIncl;
	public double rdAccuracyIncl;
	public double rdSlopeDist;
	public double rdDistTime;
	public TMC_FULL_MEAS(double rdHzAngle, double rdVAngle,
			double rdAccuracyAngle, double rdCrossIncl, double rdLengthIncl,
			double rdAccuracyIncl, double rdSlopeDist, double rdDistTime) {
		this.rdHzAngle = rdHzAngle;
		this.rdVAngle = rdVAngle;
		this.rdAccuracyAngle = rdAccuracyAngle;
		this.rdCrossIncl = rdCrossIncl;
		this.rdLengthIncl = rdLengthIncl;
		this.rdAccuracyIncl = rdAccuracyIncl;
		this.rdSlopeDist = rdSlopeDist;
		this.rdDistTime = rdDistTime;
	}
}
