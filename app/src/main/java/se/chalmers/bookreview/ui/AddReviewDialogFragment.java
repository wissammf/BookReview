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
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import se.chalmers.bookreview.R;
import se.chalmers.bookreview.net.WebRequestManager;

public class AddReviewDialogFragment extends DialogFragment implements WebRequestManager.WebRequestHandler {
    private static final String BOOK_ID_KEY = "BOOK_ID";

    private ImageView mIvQrCode;

    private int mBookId;

    public static AddReviewDialogFragment newInstance(int bookId) {
        AddReviewDialogFragment f = new AddReviewDialogFragment();

        Bundle args = new Bundle();
        args.putInt(BOOK_ID_KEY, bookId);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBookId = getArguments().getInt(BOOK_ID_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_review_dialog, container, false);

        mIvQrCode = rootView.findViewById(R.id.iv_qr_code);

        Button btnCancel = rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        WebRequestManager.getInstance().getNewReviewCode(mBookId, this);

        return rootView;
    }

    @Override
    public void onSuccess(Object data) {
        String qrCode = (String) data;
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
            mIvQrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure() {
        dismiss();
        Toast.makeText(getContext(), R.string.error_server, Toast.LENGTH_SHORT).show();
    }
}
