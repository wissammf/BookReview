package se.chalmers.bookreview.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import se.chalmers.bookreview.R;

public class AddReviewDialogFragment extends DialogFragment {
    private static final String BOOK_ID_KEY = "BOOK_ID";
    private static final String BOOK_TITLE_KEY = "BOOK_TITLE_KEY";

    private int mBookId;
    private String mBookTitle;
    private Bitmap mQrCode;

    public static AddReviewDialogFragment newInstance(int bookId, String bookTitle) {
        AddReviewDialogFragment f = new AddReviewDialogFragment();

        Bundle args = new Bundle();
        args.putInt(BOOK_ID_KEY, bookId);
        args.putString(BOOK_TITLE_KEY, bookTitle);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBookId = getArguments().getInt(BOOK_ID_KEY);
        mBookTitle = getArguments().getString(BOOK_TITLE_KEY);

        createQrCode();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_review_dialog, container, false);

        ImageView ivQrCode = rootView.findViewById(R.id.iv_qr_code);
        ivQrCode.setImageBitmap(mQrCode);

        Button btnCancel = rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }

    public void createQrCode() {
        String qrCode = String.format("%s@%s", String.valueOf(mBookId), mBookTitle);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrCode, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            mQrCode = bmp;

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
