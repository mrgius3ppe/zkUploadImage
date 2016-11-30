package com.org.UploadMVVM;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Image;

import com.conection.pool.ByteImageTest;

public class UploadImageMVVM {
	private AImage myImage;

	
	
	public AImage getMyImage() {
		return myImage;
	}
	public void setMyImage(AImage myImage) {
		this.myImage = myImage;
	}
	ByteImageTest imagemvvm=new ByteImageTest();
	
	@Command("upload")
	@NotifyChange("myImage")
	public void onImageUpload(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
			throws Exception {
		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			org.zkoss.util.media.Media media = upEvent.getMedia();
			int lengthofImage = media.getByteData().length;

			if (media instanceof org.zkoss.image.Image) {

				
				if (lengthofImage > 800 * 1024) {
					Notificacion("Por favor seleccion una imagen menor de 800Kb.");
					return;
				} else {
					myImage = (AImage) media;
					
				}
				imagemvvm.insertByte(media.getName(), media.getStreamData());
				byte [] imagebyte=imagemvvm.writeBlob(media.getName());
				myImage=new AImage("images/logo.jpg",imagebyte);
				myImage = (AImage) media;
				
			} else {
				Messagebox.show("No es una Imagen: " + media, "Error",
						Messagebox.OK, Messagebox.ERROR);

			}

		} else {
			Notificacion("Problemas al subir..!.");
		}

	}
	protected void Notificacion(String message) {
		Messagebox.show(message, "Alerta !!", Messagebox.OK,
				Messagebox.INFORMATION);
	}
	
}
