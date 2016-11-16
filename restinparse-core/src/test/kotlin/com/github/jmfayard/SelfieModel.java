package com.github.jmfayard;

import com.github.jmfayard.ParseQuery.Builder;
import com.github.jmfayard.model.ParsePtr;

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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Attachment", objectId);
        }

        @Override
        public String className() {
            return "Attachment";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Blacklist", objectId);
        }

        @Override
        public String className() {
            return "Blacklist";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ChatAbstract", objectId);
        }

        @Override
        public String className() {
            return "ChatAbstract";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ChatMessage", objectId);
        }

        @Override
        public String className() {
            return "ChatMessage";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Comment", objectId);
        }

        @Override
        public String className() {
            return "Comment";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Event", objectId);
        }

        @Override
        public String className() {
            return "Event";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("EventGroup", objectId);
        }

        @Override
        public String className() {
            return "EventGroup";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ImageUploadAdapter", objectId);
        }

        @Override
        public String className() {
            return "ImageUploadAdapter";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("JobLog", objectId);
        }

        @Override
        public String className() {
            return "JobLog";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Like", objectId);
        }

        @Override
        public String className() {
            return "Like";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("PrivatePicturesRequest", objectId);
        }

        @Override
        public String className() {
            return "PrivatePicturesRequest";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("ProfileVisit", objectId);
        }

        @Override
        public String className() {
            return "ProfileVisit";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("PushesToSend", objectId);
        }

        @Override
        public String className() {
            return "PushesToSend";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("Subscriptions", objectId);
        }

        @Override
        public String className() {
            return "Subscriptions";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("TestObject", objectId);
        }

        @Override
        public String className() {
            return "TestObject";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("UsageRight", objectId);
        }

        @Override
        public String className() {
            return "UsageRight";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("UserBlocked", objectId);
        }

        @Override
        public String className() {
            return "UserBlocked";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("WarnLog", objectId);
        }

        @Override
        public String className() {
            return "WarnLog";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Installation", objectId);
        }

        @Override
        public String className() {
            return "_Installation";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Role", objectId);
        }

        @Override
        public String className() {
            return "_Role";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_Session", objectId);
        }

        @Override
        public String className() {
            return "_Session";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("_User", objectId);
        }

        @Override
        public String className() {
            return "_User";
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
        }

        public static ParsePtr pointer(String objectId) {
            return new ParsePtr("TempChatMessage", objectId);
        }

        @Override
        public String className() {
            return "TempChatMessage";
        }


    }
}
