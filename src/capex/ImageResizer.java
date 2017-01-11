
/*
 * This file is part of Jkop for Android
 * Copyright (c) 2016-2017 Job and Esther Technologies, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package capex;

public class ImageResizer
{
	public static int li(double src1, double src2, double a) {
		return((int)((a * src2) + ((1 - a) * src1)));
	}

	public static double bilinearInterpolation(int q11, int q21, int q12, int q22, double tx, double ty) {
		return((double)li((double)li((double)q11, (double)q21, tx), (double)li((double)q12, (double)q22, tx), ty));
	}

	public static BitmapBuffer resizeBilinear(BitmapBuffer bmpbuf, int anw, int anh) {
		if((anw == 0) || (anh == 0)) {
			return(null);
		}
		if((anw < 0) && (anh < 0)) {
			return(bmpbuf);
		}
		byte[] src = bmpbuf.getBuffer();
		if(src == null) {
			return(null);
		}
		int sz = (int)cape.Buffer.getSize(src);
		int ow = bmpbuf.getWidth();
		int oh = bmpbuf.getHeight();
		if((ow == anw) && (oh == anh)) {
			return(bmpbuf);
		}
		if(sz != ((ow * oh) * 4)) {
			return(null);
		}
		int nw = anw;
		int nh = anh;
		double scaler = 1.00;
		if(nw < 0) {
			scaler = ((double)nh) / ((double)oh);
		}
		else if(nh < 0) {
			scaler = ((double)nw) / ((double)ow);
		}
		if(scaler != 1.00) {
			nw = (int)(((double)ow) * scaler);
			nh = (int)(((double)oh) * scaler);
		}
		byte[] dest = new byte[(nw * nh) * 4];
		if(dest == null) {
			return(null);
		}
		byte[] desp = dest;
		byte[] srcp = src;
		int dx = 0;
		int dy = 0;
		double stepx = (double)((ow - 1.00) / (nw - 1.00));
		double stepy = (double)((oh - 1.00) / (nh - 1.00));
		for(dy = 0 ; dy < nh ; dy++) {
			for(dx = 0 ; dx < nw ; dx++) {
				double ptx = (double)(dx * stepx);
				double pty = (double)(dy * stepy);
				int ix = (int)ptx;
				int iy = (int)pty;
				int q11i = ((iy * ow) + ix) * 4;
				int q21i = ((iy * ow) + (ix + 1)) * 4;
				int q12i = (((iy + 1) * ow) + ix) * 4;
				int q22i = (((iy + 1) * ow) + (ix + 1)) * 4;
				int rq11 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q11i + 0);
				int gq11 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q11i + 1);
				int bq11 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q11i + 2);
				int aq11 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q11i + 3);
				int rq21 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q21i + 0);
				int gq21 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q21i + 1);
				int bq21 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q21i + 2);
				int aq21 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q21i + 3);
				int rq12 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q12i + 0);
				int gq12 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q12i + 1);
				int bq12 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q12i + 2);
				int aq12 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q12i + 3);
				int rq22 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q22i + 0);
				int gq22 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q22i + 1);
				int bq22 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q22i + 2);
				int aq22 = capex.ImageFilterUtil.getSafeByte(srcp, sz, q22i + 3);
				int resr = (int)bilinearInterpolation(rq11, rq21, rq12, rq22, ptx - ix, pty - iy);
				int resg = (int)bilinearInterpolation(gq11, gq21, gq12, gq22, ptx - ix, pty - iy);
				int resb = (int)bilinearInterpolation(bq11, bq21, bq12, bq22, ptx - ix, pty - iy);
				int resa = (int)bilinearInterpolation(aq11, aq21, aq12, aq22, ptx - ix, pty - iy);
				cape.Buffer.setByte(desp, (long)((((dy * nw) + dx) * 4) + 0), (byte)resr);
				cape.Buffer.setByte(desp, (long)((((dy * nw) + dx) * 4) + 1), (byte)resg);
				cape.Buffer.setByte(desp, (long)((((dy * nw) + dx) * 4) + 2), (byte)resb);
				cape.Buffer.setByte(desp, (long)((((dy * nw) + dx) * 4) + 3), (byte)resa);
			}
		}
		return(capex.BitmapBuffer.create(dest, nw, nh));
	}

	public static void untransformCoords(Matrix33 m, int ix, int iy, double[] tu, double[] tv, double[] tw) {
		double x = (double)(ix + 0.50);
		double y = (double)(iy + 0.50);
		tu[0] = ((m.v[0] * (x + 0)) + (m.v[3] * (y + 0))) + m.v[6];
		tv[0] = ((m.v[1] * (x + 0)) + (m.v[4] * (y + 0))) + m.v[7];
		tw[0] = ((m.v[2] * (x + 0)) + (m.v[5] * (y + 0))) + m.v[8];
		tu[1] = ((m.v[0] * (x - 1)) + (m.v[3] * (y + 0))) + m.v[6];
		tv[1] = ((m.v[1] * (x - 1)) + (m.v[4] * (y + 0))) + m.v[7];
		tw[1] = ((m.v[2] * (x - 1)) + (m.v[5] * (y + 0))) + m.v[8];
		tu[2] = ((m.v[0] * (x + 0)) + (m.v[3] * (y - 1))) + m.v[6];
		tv[2] = ((m.v[1] * (x + 0)) + (m.v[4] * (y - 1))) + m.v[7];
		tw[2] = ((m.v[2] * (x + 0)) + (m.v[5] * (y - 1))) + m.v[8];
		tu[3] = ((m.v[0] * (x + 1)) + (m.v[3] * (y + 0))) + m.v[6];
		tv[3] = ((m.v[1] * (x + 1)) + (m.v[4] * (y + 0))) + m.v[7];
		tw[3] = ((m.v[2] * (x + 1)) + (m.v[5] * (y + 0))) + m.v[8];
		tu[4] = ((m.v[0] * (x + 0)) + (m.v[3] * (y + 1))) + m.v[6];
		tv[4] = ((m.v[1] * (x + 0)) + (m.v[4] * (y + 1))) + m.v[7];
		tw[4] = ((m.v[2] * (x + 0)) + (m.v[5] * (y + 1))) + m.v[8];
	}

	public static void normalizeCoords(int count, double[] tu, double[] tv, double[] tw, double[] su, double[] sv) {
		int i = 0;
		for(i = 0 ; i < count ; i++) {
			if(tw[i] != 0.00) {
				su[i] = (tu[i] / tw[i]) - 0.50;
				sv[i] = (tv[i] / tw[i]) - 0.50;
			}
			else {
				su[i] = tu[i];
				sv[i] = tv[i];
			}
		}
	}

	public static final int FIXED_SHIFT = 10;
	private static int unit = 0;

	public static void initFixedUnit() {
		unit = 1 << FIXED_SHIFT;
	}

	public static int double2Fixed(double val) {
		initFixedUnit();
		return((int)(val * unit));
	}

	public static double fixed2Double(double val) {
		initFixedUnit();
		return(val / unit);
	}

	public static boolean superSampleDtest(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
		return((((((((cape.Math.abs(x0 - x1) > cape.Math.M_SQRT2) || (cape.Math.abs(x1 - x2) > cape.Math.M_SQRT2)) || (cape.Math.abs(x2 - x3) > cape.Math.M_SQRT2)) || (cape.Math.abs(x3 - x0) > cape.Math.M_SQRT2)) || (cape.Math.abs(y0 - y1) > cape.Math.M_SQRT2)) || (cape.Math.abs(y1 - y2) > cape.Math.M_SQRT2)) || (cape.Math.abs(y2 - y3) > cape.Math.M_SQRT2)) || (cape.Math.abs(y3 - y0) > cape.Math.M_SQRT2));
	}

	public static boolean supersampleTest(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
		initFixedUnit();
		return((((((((cape.Math.abs(x0 - x1) > unit) || (cape.Math.abs(x1 - x2) > unit)) || (cape.Math.abs(x2 - x3) > unit)) || (cape.Math.abs(x3 - x0) > unit)) || (cape.Math.abs(y0 - y1) > unit)) || (cape.Math.abs(y1 - y2) > unit)) || (cape.Math.abs(y2 - y3) > unit)) || (cape.Math.abs(y3 - y0) > unit));
	}

	public static int lerp(int v1, int v2, int r) {
		initFixedUnit();
		return(((v1 * (unit - r)) + (v2 * r)) >> FIXED_SHIFT);
	}

	public static void sampleBi(RGBAPixelIntegerBuffer pixels, int x, int y, int[] color) {
		initFixedUnit();
		int xscale = x & (unit - 1);
		int yscale = y & (unit - 1);
		int x0 = x >> FIXED_SHIFT;
		int y0 = y >> FIXED_SHIFT;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		int i = 0;
		int[] c0 = pixels.getRgbaPixel(x0, y0, true);
		int[] c1 = pixels.getRgbaPixel(x1, y0, true);
		int[] c2 = pixels.getRgbaPixel(x0, y1, true);
		int[] c3 = pixels.getRgbaPixel(x1, y1, true);
		color[3] = lerp(lerp(c0[3], c1[3], yscale), lerp(c2[3], c3[3], yscale), xscale);
		if(color[3] != 0) {
			for(i = 0 ; i < 3 ; i++) {
				color[i] = lerp(lerp((c0[i] * c0[3]) / 255, (c1[i] * c1[3]) / 255, yscale), lerp((c2[i] * c2[3]) / 255, (c3[i] * c3[3]) / 255, yscale), xscale);
			}
		}
		else {
			for(i = 0 ; i < 3 ; i++) {
				color[i] = 0;
			}
		}
	}

	public static void getSample(RGBAPixelIntegerBuffer pixels, int xc, int yc, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3, int cciv, int level, int[] color) {
		if((level == 0) || (supersampleTest((double)x0, (double)y0, (double)x1, (double)y1, (double)x2, (double)y2, (double)x3, (double)y3) == false)) {
			int i = 0;
			int[] c = new int[4];
			sampleBi(pixels, xc, yc, c);
			for(i = 0 ; i < 4 ; i++) {
				color[i] = color[i] + c[i];
			}
		}
		else {
			int tx = 0;
			int lx = 0;
			int rx = 0;
			int bx = 0;
			int tlx = 0;
			int trx = 0;
			int blx = 0;
			int brx = 0;
			int ty = 0;
			int ly = 0;
			int ry = 0;
			int by = 0;
			int tly = 0;
			int trz = 0;
			int bly = 0;
			int bry = 0;
			tx = (x0 + x1) / 2;
			tlx = (x0 + xc) / 2;
			trx = (x1 + xc) / 2;
			lx = (x0 + x3) / 2;
			rx = (x1 + x2) / 2;
			blx = (x2 + xc) / 2;
			brx = (x3 + xc) / 2;
			bx = (x3 + x2) / 2;
			ty = (y0 + y1) / 2;
			tly = (y0 + yc) / 2;
			trz = (y1 + yc) / 2;
			ly = (y0 + y3) / 2;
			ry = (y1 + y2) / 2;
			bly = (y3 + yc) / 2;
			bry = (y2 + yc) / 2;
			by = (y3 + y2) / 2;
			getSample(pixels, tlx, tly, x0, y0, tx, ty, xc, yc, lx, ly, cciv, level - 1, color);
			getSample(pixels, trx, trz, tx, ty, x1, y1, rx, ry, xc, yc, cciv, level - 1, color);
			getSample(pixels, brx, bry, xc, yc, rx, ry, x2, y2, bx, by, cciv, level - 1, color);
			getSample(pixels, blx, bly, lx, ly, xc, yc, bx, by, x3, y3, cciv, level - 1, color);
		}
	}

	public static void sampleAdapt(RGBAPixelIntegerBuffer src, double xc, double yc, double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3, IndexMovingBuffer dest) {
		int cc = 0;
		int i = 0;
		int[] c = new int[4];
		int cciv = 0;
		getSample(src, double2Fixed(xc), double2Fixed(yc), double2Fixed(x0), double2Fixed(y0), double2Fixed(x1), double2Fixed(y1), double2Fixed(x2), double2Fixed(y2), double2Fixed(x3), double2Fixed(y3), cciv, 3, c);
		cc = cciv;
		if(cc == 0) {
			cc = 1;
		}
		int aa = c[3] / cc;
		cape.Buffer.setByte(dest.getBuf(), (long)3, (byte)aa);
		if(aa != 0) {
			for(i = 0 ; i < 3 ; i++) {
				cape.Buffer.setByte(dest.getBuf(), (long)i, (byte)(((c[i] / cc) * 255) / aa));
			}
		}
		else {
			for(i = 0 ; i < 3 ; i++) {
				cape.Buffer.setByte(dest.getBuf(), (long)i, (byte)0);
			}
		}
	}

	public static double drawableTransformCubic(double x, int jm1, int j, int jp1, int jp2) {
		return((double)(j + ((0.50 * x) * ((jp1 - jm1) + (x * (((((2.00 * jm1) - (5.00 * j)) + (4.00 * jp1)) - jp2) + (x * (((3.00 * (j - jp1)) + jp2) - jm1))))))));
	}

	public static class IndexMovingBuffer
	{
		private byte[] buf = null;
		private long index = (long)0;

		public IndexMovingBuffer move(int n) {
			IndexMovingBuffer v = new IndexMovingBuffer();
			v.setBuf(buf);
			v.setIndex(v.getIndex() + n);
			return(v);
		}

		public byte[] getBuf() {
			return(buf);
		}

		public IndexMovingBuffer setBuf(byte[] v) {
			buf = v;
			return(this);
		}

		public long getIndex() {
			return(index);
		}

		public IndexMovingBuffer setIndex(long v) {
			index = v;
			return(this);
		}
	}

	public static int cubicRow(double dx, IndexMovingBuffer row) {
		return((int)drawableTransformCubic(dx, (int)cape.Buffer.getByte(row.getBuf(), (long)0), (int)cape.Buffer.getByte(row.getBuf(), (long)4), (int)cape.Buffer.getByte(row.getBuf(), (long)8), (int)cape.Buffer.getByte(row.getBuf(), (long)12)));
	}

	public static int cubicScaledRow(double dx, IndexMovingBuffer row, IndexMovingBuffer arow) {
		return((int)drawableTransformCubic(dx, (int)(cape.Buffer.getByte(row.getBuf(), (long)0) * cape.Buffer.getByte(arow.getBuf(), (long)0)), (int)(cape.Buffer.getByte(row.getBuf(), (long)4) * cape.Buffer.getByte(arow.getBuf(), (long)4)), (int)(cape.Buffer.getByte(row.getBuf(), (long)8) * cape.Buffer.getByte(arow.getBuf(), (long)8)), (int)(cape.Buffer.getByte(row.getBuf(), (long)12) * cape.Buffer.getByte(arow.getBuf(), (long)12))));
	}

	public static void sampleCubic(PixelRegionBuffer src, double su, double sv, IndexMovingBuffer dest) {
		double aval = 0.00;
		double arecip = 0.00;
		int i = 0;
		int iu = (int)cape.Math.floor(su);
		int iv = (int)cape.Math.floor(sv);
		int stride = src.getStride();
		double du = 0.00;
		double dv = 0.00;
		byte[] br = src.getBufferRegion(iu - 1, iv - 1);
		if(br == null) {
			return;
		}
		dest.setBuf(br);
		du = su - iu;
		dv = sv - iv;
		aval = drawableTransformCubic(dv, cubicRow(du, dest.move(3 + (stride * 0))), cubicRow(du, dest.move(3 + (stride * 1))), cubicRow(du, dest.move(3 + (stride * 2))), cubicRow(du, dest.move(3 + (stride * 3))));
		if(aval <= 0) {
			arecip = 0.00;
			cape.Buffer.setByte(dest.getBuf(), (long)3, (byte)0);
		}
		else if(aval > 255.00) {
			arecip = 1.00 / aval;
			cape.Buffer.setByte(dest.getBuf(), (long)3, (byte)255);
		}
		else {
			arecip = 1.00 / aval;
			cape.Buffer.setByte(dest.getBuf(), (long)3, (byte)((int)cape.Math.rint(aval)));
		}
		for(i = 0 ; i < 3 ; i++) {
			int v = (int)cape.Math.rint(arecip * drawableTransformCubic(dv, cubicScaledRow(du, dest.move(i + (stride * 0)), dest.move(3 + (stride * 0))), cubicScaledRow(du, dest.move(i + (stride * 1)), dest.move(3 + (stride * 1))), cubicScaledRow(du, dest.move(i + (stride * 2)), dest.move(3 + (stride * 2))), cubicScaledRow(du, dest.move(i + (stride * 3)), dest.move(3 + (stride * 3)))));
			cape.Buffer.setByte(dest.getBuf(), (long)i, (byte)capex.ImageFilterUtil.clamp((double)v));
		}
	}

	public static BitmapBuffer resizeBicubic(BitmapBuffer bb, int anw, int anh) {
		if((anw == 0) || (anh == 0)) {
			return(null);
		}
		if((anw < 0) && (anh < 0)) {
			return(bb);
		}
		byte[] sb = bb.getBuffer();
		if(sb == null) {
			return(null);
		}
		int w = bb.getWidth();
		int h = bb.getHeight();
		double scaler = 1.00;
		int nw = anw;
		int nh = anh;
		if(nw < 0) {
			scaler = ((double)nh) / ((double)h);
		}
		else if(nh < 0) {
			scaler = ((double)nw) / ((double)w);
		}
		if(scaler != 1.00) {
			nw = (int)(((double)w) * scaler);
			nh = (int)(((double)h) * scaler);
		}
		byte[] v = new byte[(nw * nh) * 4];
		IndexMovingBuffer destp = null;
		destp.setBuf(v);
		int y = 0;
		double sx = ((double)nw) / ((double)w);
		double sy = ((double)nh) / ((double)h);
		Matrix33 matrix = capex.Matrix33.forScale(sx, sy);
		matrix = capex.Matrix33.invertMatrix(matrix);
		double uinc = matrix.v[0];
		double vinc = matrix.v[1];
		double winc = matrix.v[2];
		RGBAPixelIntegerBuffer pixels = capex.RGBAPixelIntegerBuffer.create(sb, w, h);
		PixelRegionBuffer pixrgn = capex.PixelRegionBuffer.forRgbaPixels(pixels, 4, 4);
		double[] tu = new double[5];
		double[] tv = new double[5];
		double[] tw = new double[5];
		double[] su = new double[5];
		double[] sv = new double[5];
		for(y = 0 ; y < nh ; y++) {
			untransformCoords(matrix, 0, y, tu, tv, tw);
			int width = nw;
			while(width-- > 0) {
				int i = 0;
				normalizeCoords(5, tu, tv, tw, su, sv);
				if(superSampleDtest(su[1], sv[1], su[2], sv[2], su[3], sv[3], su[4], sv[4])) {
					sampleAdapt(pixels, su[0], sv[0], su[1], sv[1], su[2], sv[2], su[3], sv[3], su[4], sv[4], destp);
				}
				else {
					sampleCubic(pixrgn, su[0], sv[0], destp);
				}
				destp = destp.move(4);
				for(i = 0 ; i < 5 ; i++) {
					tu[i] = tu[i] + uinc;
					tv[i] = tv[i] + vinc;
					tw[i] = tw[i] + winc;
				}
			}
		}
		return(capex.BitmapBuffer.create(v, nw, nh));
	}
}
