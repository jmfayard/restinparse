package com.github.jmfayard;

import com.github.jmfayard.model.ParsePtr;
import com.github.jmfayard.ParseQuery.Builder;

public class SelfieModel {
    public enum AndroidChatMessage implements ParseClass {
        ACL,
        createdAt,
        objectId,
        updatedAt;

        public static Builder<AndroidChatMessage> query() {
            return new Builder<AndroidChatMessage>("AndroidChatMessage");
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("AndroidChatMessage", objectId);
        }

        @Override
        public String className() {
            return "AndroidChatMessage";
        }
    }


    public enum Attachment implements ParseClass {
        ACL,
        createdAt,
        file,
        file2,
        imageUpload,
        objectId,
        sender,
        type,
        updatedAt;

        public static Builder<Attachment> query() {
            return new Builder<Attachment>("Attachment");
        }        @Override
        public String className() {
            return "Attachment";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Attachment", objectId);
        }


    }


    public enum Blacklist implements ParseClass {
        ACL,
        createdAt,
        device_id,
        email,
        objectId,
        updatedAt;

        public static Builder<Blacklist> query() {
            return new Builder<Blacklist>("Blacklist");
        }        @Override
        public String className() {
            return "Blacklist";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Blacklist", objectId);
        }


    }


    public enum ChatAbstract implements ParseClass {
        ACL,
        createdAt,
        deletedAt,
        lastMessage,
        lastMessageDate,
        objectId,
        partner,
        updatedAt;

        public static Builder<ChatAbstract> query() {
            return new Builder<ChatAbstract>("ChatAbstract");
        }        @Override
        public String className() {
            return "ChatAbstract";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ChatAbstract", objectId);
        }


    }


    public enum ChatMessage implements ParseClass {
        ACL,
        attachment,
        createdAt,
        deleted,
        message,
        messageHash,
        objectId,
        partner,
        sender,
        updatedAt;

        public static Builder<ChatMessage> query() {
            return new Builder<ChatMessage>("ChatMessage");
        }        @Override
        public String className() {
            return "ChatMessage";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ChatMessage", objectId);
        }


    }


    public enum Comment implements ParseClass {
        ACL,
        commentator,
        content,
        createdAt,
        objectId,
        updatedAt;

        public static Builder<Comment> query() {
            return new Builder<Comment>("Comment");
        }        @Override
        public String className() {
            return "Comment";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Comment", objectId);
        }


    }


    public enum Event implements ParseClass {
        ACL,
        actor,
        affects,
        amount,
        comment,
        createdAt,
        image,
        objectId,
        type,
        updatedAt;

        public static Builder<Event> query() {
            return new Builder<Event>("Event");
        }        @Override
        public String className() {
            return "Event";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Event", objectId);
        }


    }


    public enum EventGroup implements ParseClass {
        ACL,
        createdAt,
        events,
        images,
        isActor,
        objectId,
        type,
        updatedAt,
        user,
        users;

        public static Builder<EventGroup> query() {
            return new Builder<EventGroup>("EventGroup");
        }        @Override
        public String className() {
            return "EventGroup";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("EventGroup", objectId);
        }


    }


    public enum ImageUploadAdapter implements ParseClass {
        ACL,
        blurredImage,
        buyers,
        comments,
        createdAt,
        deletedAt,
        description,
        file,
        fskReviewed,
        image,
        inflatedPrice,
        likers,
        objectId,
        originalImage,
        reportedAt,
        reporter,
        roses,
        status,
        tag,
        title,
        updatedAt,
        uploader,
        visibility;

        public static Builder<ImageUploadAdapter> query() {
            return new Builder<ImageUploadAdapter>("ImageUploadAdapter");
        }        @Override
        public String className() {
            return "ImageUploadAdapter";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ImageUploadAdapter", objectId);
        }


    }


    public enum JobLog implements ParseClass {
        ACL,
        createdAt,
        description,
        errors,
        objectId,
        objects,
        params,
        updatedAt,
        urls;

        public static Builder<JobLog> query() {
            return new Builder<JobLog>("JobLog");
        }        @Override
        public String className() {
            return "JobLog";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("JobLog", objectId);
        }


    }


    public enum Like implements ParseClass {
        ACL,
        createdAt,
        image,
        objectId,
        updatedAt,
        user;

        public static Builder<Like> query() {
            return new Builder<Like>("Like");
        }        @Override
        public String className() {
            return "Like";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Like", objectId);
        }


    }


    public enum PrivatePicturesRequest implements ParseClass {
        ACL,
        createdAt,
        objectId,
        owner,
        permitted,
        requester,
        updatedAt;

        public static Builder<PrivatePicturesRequest> query() {
            return new Builder<PrivatePicturesRequest>("PrivatePicturesRequest");
        }        @Override
        public String className() {
            return "PrivatePicturesRequest";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("PrivatePicturesRequest", objectId);
        }


    }


    public enum ProfileVisit implements ParseClass {
        ACL,
        createdAt,
        lastTimeNotified,
        objectId,
        updatedAt,
        visited,
        visitor,
        visitsCount;

        public static Builder<ProfileVisit> query() {
            return new Builder<ProfileVisit>("ProfileVisit");
        }        @Override
        public String className() {
            return "ProfileVisit";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ProfileVisit", objectId);
        }


    }


    public enum PushesToSend implements ParseClass {
        ACL,
        alreadySent,
        createdAt,
        objectId,
        text,
        toUser,
        updatedAt;

        public static Builder<PushesToSend> query() {
            return new Builder<PushesToSend>("PushesToSend");
        }        @Override
        public String className() {
            return "PushesToSend";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("PushesToSend", objectId);
        }


    }


    public enum Subscriptions implements ParseClass {
        ACL,
        blocked,
        createdAt,
        objectId,
        subscriber,
        subscriberOf,
        updatedAt,
        useMasterKey;

        public static Builder<Subscriptions> query() {
            return new Builder<Subscriptions>("Subscriptions");
        }        @Override
        public String className() {
            return "Subscriptions";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Subscriptions", objectId);
        }


    }


    public enum TestObject implements ParseClass {
        ACL,
        createdAt,
        foo,
        objectId,
        updatedAt;

        public static Builder<TestObject> query() {
            return new Builder<TestObject>("TestObject");
        }        @Override
        public String className() {
            return "TestObject";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("TestObject", objectId);
        }


    }


    public enum UsageRight implements ParseClass {
        ACL,
        createdAt,
        image,
        objectId,
        updatedAt,
        user;

        public static Builder<UsageRight> query() {
            return new Builder<UsageRight>("UsageRight");
        }        @Override
        public String className() {
            return "UsageRight";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("UsageRight", objectId);
        }


    }


    public enum UserBlocked implements ParseClass {
        ACL,
        createdAt,
        email,
        objectId,
        updatedAt,
        user;

        public static Builder<UserBlocked> query() {
            return new Builder<UserBlocked>("UserBlocked");
        }        @Override
        public String className() {
            return "UserBlocked";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("UserBlocked", objectId);
        }


    }


    public enum WarnLog implements ParseClass {
        ACL,
        createdAt,
        message,
        objectId,
        updatedAt,
        user;

        public static Builder<WarnLog> query() {
            return new Builder<WarnLog>("WarnLog");
        }        @Override
        public String className() {
            return "WarnLog";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("WarnLog", objectId);
        }


    }


    public enum _Installation implements ParseClass {
        ACL,
        GCMSenderId,
        appIdentifier,
        appName,
        appVersion,
        badge,
        buildNumber,
        channels,
        createdAt,
        device,
        deviceToken,
        deviceType,
        installationId,
        localeIdentifier,
        objectId,
        parseVersion,
        pushType,
        timeZone,
        updatedAt,
        user;

        public static Builder<_Installation> query() {
            return new Builder<_Installation>("_Installation");
        }        @Override
        public String className() {
            return "_Installation";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Installation", objectId);
        }


    }


    public enum _Role implements ParseClass {
        ACL,
        createdAt,
        name,
        objectId,
        roles,
        updatedAt,
        users;

        public static Builder<_Role> query() {
            return new Builder<_Role>("_Role");
        }        @Override
        public String className() {
            return "_Role";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Role", objectId);
        }


    }


    public enum _Session implements ParseClass {
        ACL,
        createdAt,
        createdWith,
        expiresAt,
        installationId,
        objectId,
        restricted,
        sessionToken,
        updatedAt,
        user;

        public static Builder<_Session> query() {
            return new Builder<_Session>("_Session");
        }        @Override
        public String className() {
            return "_Session";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Session", objectId);
        }


    }


    public enum _User implements ParseClass {
        campoint,
        _id,
        _created_at,
        _updated_at,
        ACL,
        acceptedPushes,
        authData,
        birthday,
        blocked,
        blockedUsers,
        callsAccepted,
        chatUser,
        countryCode,
        createdAt,
        dateFskCheck,
        deleteState,
        deletedAbstracts,
        device_id,
        email,
        emailBonusAlreadyPaid,
        emailLower,
        emailLowerCase,
        emailVerified,
        facebookId,
        fskCheckCompleted,
        fskCheckHandled,
        gender,
        hasContent,
        home,
        idCardImage,
        image,
        imageModified,
        imageThird,
        installation_id,
        lastImageSearches,
        lastTimeOnline,
        lastUserSearches,
        locality,
        numberIsVerified,
        objectId,
        password,
        phoneNumber,
        portraitImage,
        privacyMode,
        pushSent,
        rate,
        status,
        statusLong,
        updatedAt,
        username,
        validatedImage;

        public static Builder<_User> query() {
            return new Builder<_User>("_User");
        }        @Override
        public String className() {
            return "_User";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_User", objectId);
        }


    }


    public enum TempChatMessage implements ParseClass {
        ACL,
        createdAt,
        messageHash,
        objectId,
        receiver,
        sender,
        updatedAt;

        public static Builder<TempChatMessage> query() {
            return new Builder<TempChatMessage>("TempChatMessage");
        }        @Override
        public String className() {
            return "TempChatMessage";
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("TempChatMessage", objectId);
        }


    }
}
