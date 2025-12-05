///***********************************************************************
//* Copyright by Michael Loesler, https://software.applied-geodesy.org   *
//*                                                                      *
//* This program is free software; you can redistribute it and/or modify *
//* it under the terms of the GNU General Public License as published by *
//* the Free Software Foundation; either version 3 of the License, or    *
//* at your option any later version.                                    *
//*                                                                      *
//* This program is distributed in the hope that it will be useful,      *
//* but WITHOUT ANY WARRANTY; without even the implied warranty of       *
//* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
//* GNU General Public License for more details.                         *
//*                                                                      *
//* You should have received a copy of the GNU General Public License    *
//* along with this program; if not, see <http://www.gnu.org/licenses/>  *
//* or write to the                                                      *
//* Free Software Foundation, Inc.,                                      *
//* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
//*                                                                      *
//***********************************************************************/
//
//package org.applied_geodesy.instrument.totalstaion.leica.test;
//
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//import org.applied_geodesy.instrument.totalstaion.leica.GCDataPacket;
//import org.applied_geodesy.instrument.totalstaion.leica.JGeoCOM;
//import org.applied_geodesy.instrument.totalstaion.leica.data.AUT_FINE_ADJUST;
//import org.applied_geodesy.instrument.totalstaion.leica.data.DATIME;
//import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_COORDINATE;
//import org.applied_geodesy.instrument.totalstaion.leica.data.TMC_FULL_MEAS;
//import org.applied_geodesy.instrument.totalstaion.leica.es.BAP_USER_MEASPRG;
//import org.applied_geodesy.instrument.totalstaion.leica.es.ON_OFF_TYPE;
//import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_INCLINE_PRG;
//import org.applied_geodesy.instrument.totalstaion.leica.es.TMC_MEASURE_PRG;
//
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//
//public class TrackingTest implements PropertyChangeListener {
//
//	public static void main(String args[]) throws Exception {
//		TrackingTest foo = new TrackingTest();
//		JGeoCOM geoCOM = new JGeoCOM();
//		geoCOM.addPropertyChangeListener(foo);
//		try {
//
//			boolean open = geoCOM.open(CommPortIdentifier.getPortIdentifier("COM4"), 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//
//			if (!open) {
//				System.err.println("Port kann nicht geoeffnet werden!");
//				return;
//			}
//			GCDataPacket packet = null;
//			
//			packet = geoCOM.BAP_GetMeasPrg();
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			// EDM_CONT_FAST(9)
//			packet = geoCOM.BAP_SetMeasPrg(BAP_USER_MEASPRG.BAP_CONT_REF_SYNCHRO); // .BAP_CONT_REF_SYNCHRO
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			
//			packet = geoCOM.BAP_GetMeasPrg();
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//
////			packet = geoCOM.TMC_SetEdmMode(EDM_MODE.EDM_CONT_FAST);
////			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			
//			// TRACKING
//			AUT_FINE_ADJUST fineAdjust = new AUT_FINE_ADJUST(0.08, 0.08);
//			packet = geoCOM.AUT_FineAdjust(fineAdjust);
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			
//			packet = geoCOM.AUS_SetUserLockState(ON_OFF_TYPE.ON);
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			
//			packet = geoCOM.AUT_LockIn();
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			
//			packet = geoCOM.TMC_DoMeasure(TMC_MEASURE_PRG.TMC_DEF_DIST, TMC_INCLINE_PRG.TMC_AUTO_INC);
//			System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
//			for (int i=0; i<10; i++) {
////				packet = geoCOM.TMC_DoMeasure(TMC_MEASURE_PRG.TMC_DEF_DIST, TMC_INCLINE_PRG.TMC_AUTO_INC);
////				System.out.println(packet.getGRC()+"   "+packet.getOnAnswerArgument());
////				Thread.sleep(1500);
//				packet = geoCOM.TMC_GetFullMeas(12500L, TMC_INCLINE_PRG.TMC_AUTO_INC );
//				TMC_FULL_MEAS meas = (TMC_FULL_MEAS)packet.getOnAnswerArgument();
//				System.out.println(meas.rdHzAngle+"  "+meas.rdVAngle+"   "+meas.rdSlopeDist+"  "+meas.rdAccuracyAngle+"  "+meas.rdDistTime);
//				
//				packet = geoCOM.TMC_GetCoordinate(12500L, TMC_INCLINE_PRG.TMC_AUTO_INC );
//				TMC_COORDINATE coords = (TMC_COORDINATE)packet.getOnAnswerArgument();
//				System.out.println(System.currentTimeMillis()+"  "+packet.getGRC()+"   "+coords.dE+"   "+coords.dN+"   "+coords.dH+"    "+coords.coordTime);
////				System.out.println(System.currentTimeMillis()+"  "+packet.getGRC()+"   "+coords.dE_Cont+"   "+coords.dN_Cont+"   "+coords.dH_Cont+"    "+coords.coordContTime);
//			}
//			Thread.sleep(1000);
//			packet = geoCOM.CSV_GetDateTimeCentiSec();
//			DATIME dateTime = (DATIME)packet.getOnAnswerArgument();
//			System.out.println(packet.getGRC()+"   "+dateTime.Date.Day+"   "+dateTime.Date.Month+"   "+dateTime.Date.Year);
//			System.out.println(packet.getGRC()+"   "+dateTime.Time.Hour+"   "+dateTime.Time.Minute+"   "+dateTime.Time.Second+"   "+dateTime.Time.CentiSec);
//			
////			long dact_time=  Datime.Time.Minute*60+
////					Datime.Time.Second;
//			
//			
//			packet = geoCOM.AUS_SetUserLockState(ON_OFF_TYPE.OFF);
//			packet = geoCOM.AUS_SetUserAtrState(ON_OFF_TYPE.ON);
//			packet = geoCOM.BAP_SetMeasPrg(BAP_USER_MEASPRG.BAP_SINGLE_REF_PRECISE);
//			
//		}
//		finally {
//			
//			geoCOM.close();
//		}
//	}
//
//	@Override
//	public void propertyChange(PropertyChangeEvent evt) {
//		System.out.println(evt.getPropertyName()+"   "+evt.getOldValue()+"  "+evt.getNewValue());
//	}
//}
